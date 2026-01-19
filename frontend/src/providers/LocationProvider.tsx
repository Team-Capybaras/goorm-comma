'use client'

import { createContext, useContext, useEffect, useState } from 'react'

// 위도 경도 인터페이스
interface Location {
  lat: number
  lng: number
}

// 위치 관련 제공값 인터페이스
interface LocationContextValue {
  location: Location | null // 위도 경도
  locationName: string | null // 위도 경도에 따른 위치
  loading: boolean // 로딩 상태
  getCurrentLocation: () => void // 불러오는 함수
}

const LocationContext = createContext<LocationContextValue | null>(null)

export function LocationProvider({ children }: { children: React.ReactNode }) {
  const [location, setLocation] = useState<Location | null>(null)
  const [locationName, setLocationName] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  // 위도 경도 통해서 해당 위치 명칭 불러옴.
  const fetchLocationName = async (lat: number, lng: number) => {
    const res = await fetch(`/api/reverse-geocode?lat=${lat}&lng=${lng}`)
    const data = await res.json()
    setLocationName(data.locationName)
  }

  // default 좌표
  const DEFAULT_LOCATION = {
    lat: 37.563617,
    lng: 126.997611,
  }

  // default 좌표 이름
  const DEFAULT_LOCATION_NAME = '서울특별시 · 중구'

  // 현재 위치 정보 가져오는 함수
  const getCurrentLocation = () => {
    // 위치를 가져올 수 없는 브라우저일 경우
    if (!navigator.geolocation) {
      applyFallback()
      return
    }

    setLoading(true)

    // 현재 좌표 불러옴
    navigator.geolocation.getCurrentPosition(async (pos) => {
      const { latitude, longitude } = pos.coords
      setLocation({ lat: latitude, lng: longitude })
      await fetchLocationName(latitude, longitude)
      setLoading(false)
    },
    // 현재 좌표를 못 불러올 경우 (ex) 권한 거부 / 위치 못 가져옴)
     (error) => {
        console.error('Geolocation error:', error)
        applyFallback()
        setLoading(false)
    })
  }

  const applyFallback = () => {
    setLocation(DEFAULT_LOCATION)
    setLocationName(DEFAULT_LOCATION_NAME)
  }

  // 페이지 진입 시 자동 실행
  useEffect(() => {
    getCurrentLocation()
  }, [])

  return (
    <LocationContext.Provider
      value={{ location, locationName, loading, getCurrentLocation }}
    >
      {children}
    </LocationContext.Provider>
  )
}

// 현재 위치 사용 커스텀 훅
export const useLocation = () => {
  const ctx = useContext(LocationContext)
  if (!ctx) throw new Error('useLocation must be used within LocationProvider')
  return ctx
}