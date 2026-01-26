'use client'

import { useEffect, useState } from 'react'
import { Card } from '@/components/common/Card'
import ParkCardCarousel from '@/app/(view)/(main)/_component/ParkCardCarousel'
import ParkCardCarouselSkeleton from '@/app/(view)/(main)/_status/ParkCardCarouselSkeleton'
import { ParkInfo } from '@/shared/types/park-types'
import { useLocationStore } from '@/store/location.store'
import { api } from '@/shared/libs/axios'

export default function ParkIn5km() {
  const { location, loading: locationLoading } = useLocationStore()
  const [parks, setParks] = useState<ParkInfo[]>([])
  const [loading, setLoading] = useState(true)

  const fetchData = async (
    lat: number,
    lng: number
  ): Promise<ParkInfo[]> => {
    // 5개 까지만 표시
    const res = await api.get('/v1/parks/recommend', {
      params: {
        limit_distance: 5,
        latitude: lat,
        longitude: lng,
      },
    })

    return res.data.data.parks
  }

  useEffect(() => {
    if (!location) return

    setLoading(true)

    fetchData(location.lat, location.lng)
      .then(setParks)
      .catch((err) => {
        console.error('공원 조회 실패', err)
        setParks([])
      })
      .finally(() => {
        setLoading(false)
      })
  }, [location])

  // 위치 정보 없을 경우, 안 보여줌.
  if (!location || locationLoading) return null

  // 위치 정보는 있지만 5km 이내 공원이 없을 경우 안 보여줌.
  if (!loading && parks.length === 0) return null

  return (
    <>
      <div className="flex flex-col gap-3">
        <p className="text-subtitle-1-sb px-5">
          지금 <span className="text-primary">한적한 5km</span> 이내 공원
        </p>

        <Card className="w-full border-0 rounded-[0] mb s-6">
          {/* 공원 데이터 로딩 중일 때 Skeleton UI */}
          {loading && (
            <ParkCardCarouselSkeleton />
          )}

          {/* 데이터 로딩 완료 시 전환 */}
          {!loading && parks.length > 0 && (
            <ParkCardCarousel data={parks} />
          )}
        </Card>
      </div>

      {/* 섹션 구분선 */}
      <div className="w-full h-[8px] bg-gray-200"></div>
    </>
  )
}