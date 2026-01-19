'use client'

import { createContext, useContext, useEffect, useState } from 'react'

interface Location {
  lat: number
  lng: number
}

interface LocationContextValue {
  location: Location | null
  locationName: string | null
  loading: boolean
  getCurrentLocation: () => void
}

const LocationContext = createContext<LocationContextValue | null>(null)

export function LocationProvider({ children }: { children: React.ReactNode }) {
  const [location, setLocation] = useState<Location | null>(null)
  const [locationName, setLocationName] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const fetchLocationName = async (lat: number, lng: number) => {
    const res = await fetch(`/api/reverse-geocode?lat=${lat}&lng=${lng}`)
    const data = await res.json()
    setLocationName(data.locationName)
  }

  const getCurrentLocation = () => {
    if (!navigator.geolocation) return

    setLoading(true)

    navigator.geolocation.getCurrentPosition(async (pos) => {
      const { latitude, longitude } = pos.coords
      setLocation({ lat: latitude, lng: longitude })
      await fetchLocationName(latitude, longitude)
      setLoading(false)
    })
  }

  // ✅ 페이지 진입 시 자동 실행
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

export const useLocation = () => {
  const ctx = useContext(LocationContext)
  if (!ctx) throw new Error('useLocation must be used within LocationProvider')
  return ctx
}