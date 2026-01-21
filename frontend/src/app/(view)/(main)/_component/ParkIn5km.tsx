'use client'

import { useEffect, useState } from 'react'
import { Card } from '@/components/common/Card'
import ParkCardCarousel from '@/app/(view)/(main)/_component/ParkCardCarousel'
import { fetchParks } from '@/shared/libs/park-list-api'
import { ParkInfo } from '@/shared/types/park-types'
import { useLocationStore } from '@/store/location.store'


export default function ParkIn5km() {
  const { location, loading: locationLoading } = useLocationStore()

  const [parks, setParks] = useState<ParkInfo[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!location) return

    setLoading(true)

    // 현재 위치를 백에 전달 후 거리 기준 정렬
    fetchParks({
      activeOptions: [],
      activeSort: 'distance',
      location: {
        lat: location.lat,
        lng: location.lng,
      },
    })
      .then((res) => {
        // 5km 이내 공원만 표시
        const parksIn5km = res.parks.filter(
          (park) => park.distance <= 5
        )
        setParks(parksIn5km)
      })
      .catch((err) => {
        console.error('공원 조회 실패', err)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [location])

  // 만약 위치 로딩 중이거나, 공원 정보를 불러올 때, 공원이 없을 땐 반환 X
  if (locationLoading || loading || parks.length === 0) return null

  return (
    <div className="flex flex-col gap-3">
      <p className="text-subtitle-1-sb px-5">
        지금 <span className="text-primary">한적한 5km</span> 이내 공원
      </p>

      <Card className="w-full border-0 rounded-[0] mb s-6">
        <ParkCardCarousel data={parks} />
      </Card>
    </div>
  )
}