'use client'

import { useEffect, useState } from 'react'
import MainMap from '@/components/map/MainMap'
import { getParkList } from '@/shared/libs/parkApi'
import type { ParkItem } from '@/shared/types/map-types'

export default function MapPage() {
  const [parkData, setParkData] = useState<ParkItem[]>([])

  // 1. 초기 중심 좌표 설정
  const [center, setCenter] = useState({ lat: 37.5284, lng: 126.9331 })

  const fetchParkList = async (lat: number, lng: number) => {
    try {
      const parks = await getParkList(lat, lng)
      setParkData(parks)
    } catch (error) {
      console.error('공원 목록 로딩 실패:', error)
    }
  }

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords
          fetchParkList(latitude, longitude)
        },
        (err) => {
          console.error('위치 권한 에러:', err)
          fetchParkList(37.5284, 126.9331)
        }
      )
    } else {
      fetchParkList(37.5284, 126.9331)
    }
  }, [])

  return (
    <div className="w-full h-[100dvh] relative bg-default overflow-hidden">
      <MainMap data={parkData} center={center} />
    </div>
  )
}
