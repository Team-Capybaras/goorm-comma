import { create } from 'zustand'
import { persist } from 'zustand/middleware'

// 위도 경도 인터페이스
interface Location {
  lat: number
  lng: number
}

// 위치 관련 제공값 및 저장 함수 인터페이스
interface LocationStore {
  location: Location | null
  locationName: string | null
  loading: boolean

  setLocation: (loc: Location) => void
  setLocationName: (name: string) => void
  fetchCurrentLocation: () => Promise<void>
  initLocation: () => void
}

// default 설정 위치
const DEFAULT_LOCATION: Location = {
  lat: 37.563617,
  lng: 126.997611,
}

const DEFAULT_LOCATION_NAME = '서울특별시 · 중구'

export const useLocationStore = create<LocationStore>()(
  persist(
    (set, get) => ({
      location: null,
      locationName: null,
      loading: false,

      setLocation: (location) => set({ location }),
      setLocationName: (locationName) => set({ locationName }),

      // 현재 위치 정보 가져오는 함수
      fetchCurrentLocation: async () => {
        // 위치 불러오기 안되는 브라우저일 경우
        if (!navigator.geolocation) {
          set({
            location: DEFAULT_LOCATION,
            locationName: DEFAULT_LOCATION_NAME,
            loading: false,
          })
          return
        }

        set({ loading: true })

        // Web API로 현재 위치 가져오기
        navigator.geolocation.getCurrentPosition(async (pos) => {
          const loc = {
            lat: Number(pos.coords.latitude),
            lng: Number(pos.coords.longitude),
          }

          // store에 현재 위치 저장
          set({ location: loc })

          // Kakao API를 이용한 역지오 코드
          try {
            const res = await fetch(
              `/api/reverse-geocode?lat=${loc.lat}&lng=${loc.lng}`
            )
            const data = await res.json()

            set({
              locationName: data.locationName,
              loading: false,
            })
          } catch (error) {
            // 역지오 변환 실패할 경우
            console.error('역지오 코드 변환 실패', error)
            set({
              location: DEFAULT_LOCATION,
              locationName: DEFAULT_LOCATION_NAME,
              loading: false,
            })
          }
        },
        // 현재 위치를 못 불러올 경우
        (err) => {
          console.error('현재 위치 가져오기 실패', err)
          set({
              location: DEFAULT_LOCATION,
              locationName: DEFAULT_LOCATION_NAME,
              loading: false,
            })
          },
          {
            enableHighAccuracy: true,
          }
        )
      },
      initLocation: () => {
        const { location } = get()
        if (!location) {
          get().fetchCurrentLocation()
        }
      },
    }),
    // 저장 persist 값
    {
      name: 'user-location',
      partialize: (state) => ({
        location: state.location,
        locationName: state.locationName,
      }),
    }
  )
)