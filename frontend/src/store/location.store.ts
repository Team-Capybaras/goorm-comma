import { create } from 'zustand'
import { persist } from 'zustand/middleware'

interface Location {
  lat: number
  lng: number
}

interface LocationStore {
  location: Location | null
  locationName: string | null
  loading: boolean

  setLocation: (loc: Location) => void
  setLocationName: (name: string) => void
  fetchCurrentLocation: () => Promise<void>
  initLocation: () => void
}

export const useLocationStore = create<LocationStore>()(
  persist(
    (set,get) => ({
      location: null,
      locationName: null,
      loading: false,

      setLocation: (location) => set({ location }),
      setLocationName: (locationName) => set({ locationName }),

      fetchCurrentLocation: async () => {
        if (!navigator.geolocation) return

        set({ loading: true })

        // WebAPI로 현재 위치 가져오기
        navigator.geolocation.getCurrentPosition(async (pos) => {
          const loc = {
            lat: Number(pos.coords.latitude),
            lng: Number(pos.coords.longitude),
          }

          // store에 location 저장
          set({ location: loc })

          // kakao 역지오코드
          const res = await fetch(
            `/api/reverse-geocode?lat=${loc.lat}&lng=${loc.lng}`
          )
          const data = await res.json()

          // 역지오 코드 반환값을 store에 저장
          set({
            locationName: data.locationName,
            loading: false,
          })
        },
        (err) => {
          console.error('현재 위치 가져오기 실패', err)
        },
        {
          enableHighAccuracy: true,
        })
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