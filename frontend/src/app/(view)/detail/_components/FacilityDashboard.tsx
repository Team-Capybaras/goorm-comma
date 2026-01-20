'use client'

import { useEffect, useState } from 'react'
import { api } from '@/shared/libs/axios'
import FacilityMap from '@/app/(view)/detail/_components/FacilityMap'
import {
  mapParkingToFacilityItems,
  mapTransitToFacilityItems,
} from '@/shared/utils/map-mapper'
import FacilityInfo from '@/app/(view)/detail/_components/FacilityInfo'

interface FacilityDashboardProps {
  areaCode: string
  center: {
    lat: number
    lng: number
  }
}

export default function FacilityDashboard({
    areaCode,
    center,
  }: FacilityDashboardProps) {
  const [facilities, setFacilities] = useState<any[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(false)

  useEffect(() => {
    let mounted = true

    const fetchFacilities = async () => {
      try {
        const [parkingRes, transitRes] = await Promise.all([
          api.get('/v1/parking', {
            params: { area_code: areaCode },
          }),
          api.get('/v1/transits', {
            params: { area_code: areaCode },
          }),
        ])

        if (!mounted) return

        setFacilities([
          ...mapParkingToFacilityItems(parkingRes.data.data),
          ...mapTransitToFacilityItems(transitRes.data.data),
        ])
      } catch (e) {
        console.error('시설 조회 실패', e)
        if (mounted) setError(true)
      } finally {
        if (mounted) setLoading(false)
      }
    }

    fetchFacilities()

    return () => {
      mounted = false
    }
  }, [areaCode])

  if (loading) return null // or Skeleton
  if (error) return <FacilityInfo />

  return (
    <div className="mt s-6 mb-6">
      <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
      <FacilityMap data={facilities} center={center} />
      <FacilityInfo />
    </div>
  )
}
