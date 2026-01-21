'use client'

import { useState } from 'react'

interface Location {
  lat: number
  lng: number
}

export function useCurrentLocation() {
  const [location, setLocation] = useState<Location | null>(null)
  const [locationName, setLocationName] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const fetchLocationName = async (lat: number, lng: number) => {
    const res = await fetch(`/api/reverse-geocode?lat=${lat}&lng=${lng}`)
    const data = await res.json()

    setLocationName(data.locationName)
  }

  const getCurrentLocation = () => {
    if (!navigator.geolocation) {
      setError('위치 정보를 지원하지 않는 브라우저입니다.')
      return
    }

    setLoading(true)
    setError(null)

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const { latitude, longitude } = position.coords

        setLocation({ lat: latitude, lng: longitude })
        await fetchLocationName(latitude, longitude)

        setLoading(false)
      },
      () => {
        setError('위치 정보를 가져오지 못했습니다.')
        setLoading(false)
      }
    )
  }

  return {
    location,
    locationName,
    loading,
    error,
    getCurrentLocation,
  }
}