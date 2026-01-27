'use client'

import Image from 'next/image'
import AlternativeParkClient from '@/app/(view)/detail/_components/AlternativeParkClient'
import EmblaCarousel from '@/components/common/EmblaCarousel'
import { CONGESTION_COLOR_MAP } from '@/shared/utils/congestion-helper'
import { api } from '@/shared/libs/axios'
import { ParkInfo } from '@/shared/types/park-types'
import { useEffect, useState } from 'react'
import Link from 'next/link'

interface AlternativeParkDashboardProps {
  areaCode: string
  lat: number
  lng: number
}

export default function AlternativeParkDashboard({
  areaCode,
  lat,
  lng,
}: AlternativeParkDashboardProps) {
  const [data, setData] = useState<ParkInfo[]>([])

  const fetchData = async (lat: number, lng: number): Promise<ParkInfo[]> => {
    const res = await api.get('/v1/parks/recommend', {
      params: {
        base_area_code: areaCode,
        latitude: lat,
        longitude: lng,
      },
    })

    const parks: ParkInfo[] = res.data.data.parks || []
    const uniqueParks = parks.filter((item, index, self) => {
      if (item.areaCode === areaCode) return false

      return index === self.findIndex((t) => t.areaName === item.areaName)
    })

    return uniqueParks
  }

  useEffect(() => {
    if (!lat || !lng) return

    fetchData(lat, lng)
      .then(setData)
      .catch((err) => {
        console.error('공원 조회 실패', err)
        setData([])
      })
  }, [lat, lng, areaCode])

  return (
    <div className="mt s-5 pb-8">
      <div className="flex gap s-1 relative">
        <h3 className="pl s-5 text-subtitle-2-sb">지금은 이 공원이 더 여유로워요</h3>
        <AlternativeParkClient />
      </div>
      <div className="mt s-4">
        <EmblaCarousel containerClassName={'gap-x-2 mr-6'}>
          <>
            {data?.map((item, i) => (
              <Link
                href={`/detail/${item.areaCode}`}
                className={`flex-[0_0_40%] ${i == 0 ? 'ml s-5' : ''}`}
                key={i}
              >
                <div className="relative w-[168px] h-[218px]">
                  <Image
                    src={item.images[0]}
                    alt={`park-thumbnail-${i}`}
                    fill
                    sizes="168px"
                    className="object-cover rounded-6"
                    priority={i === 0}
                  />
                </div>

                <div className="flex items-center mt s-3">
                  <p className="text-body-2-sb">{item.areaName}</p>
                  <div className="w-[3px] h-[3px] rounded-full bg-gray-300 mx s-2" />
                  <p className="text-body-2-m text-gray-500">{item.distance}km</p>
                </div>

                <p className={`text-caption-1-sb ${CONGESTION_COLOR_MAP[item.areaCongestLevel]}`}>
                  {item.areaCongestLevel}
                </p>
              </Link>
            ))}
          </>
        </EmblaCarousel>
      </div>
    </div>
  )
}
