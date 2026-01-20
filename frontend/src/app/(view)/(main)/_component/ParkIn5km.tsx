'use client'

import { useEffect, useState } from 'react'
import { Card } from '@/components/common/Card'
import ParkCardCarousel from '@/app/(view)/(main)/_component/ParkCardCarousel'
import { ParkInfo } from '@/shared/types/park-types'
import { useLocationStore } from '@/store/location.store'
import {api} from "@/shared/libs/axios";

export default function ParkIn5km() {
  const { location, loading: locationLoading } = useLocationStore()

  const [parks, setParks] = useState<ParkInfo[]>([])
  const [loading, setLoading] = useState(true)
  
  const fetchData = async (): Promise<ParkInfo[]> => {
    const res = await api.get('/v1/parks/recommend', {
      params: {
        limit_distance: 5,
        latitude: location!.lat,
        longitude: location!.lng,
      },
    })

    return res.data.data.parks
  }


  useEffect(() => {
    if (!location) return

    setLoading(true)

    fetchData()
      .then(setParks)
      .catch((err) => {
        console.error('공원 조회 실패', err)
        setParks([])
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