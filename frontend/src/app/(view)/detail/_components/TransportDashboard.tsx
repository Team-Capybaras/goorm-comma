'use client'

import DetailMap from '@/components/map/DetailMap'
import { FacilityItem } from '@/shared/types/map-types'
import Image from 'next/image'
import {useEffect, useState} from 'react'
import {api} from "@/shared/libs/axios";

const center = {
  lat: 37.5444,
  lng: 127.0374,
}

interface TransportDashboardProps {
  areaCode: string;
}

export default function TransportDashboard({ areaCode }: TransportDashboardProps) {
  const [data, setData] = useState<FacilityItem[]>([])
  const [isFullMapOpen, setIsFullMapOpen] = useState(false)

  useEffect(() => {
    const fetchData = async () => {
      const res = await api.get(`/v1/parking`, {
        params: {
          area_code: areaCode
        }
      })
      setData(res.data.data)
      console.log(res.data)
    }

    fetchData()
  }, [areaCode])

  if (!data) return null

  return (
    <div className="mt s-6 mb-6">
      <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
      <div className="w-full h-60 mt-3 rounded-xl overflow-hidden">
        <DetailMap
          data={[]}
          center={center}
          mode="preview"
          onExpand={() => setIsFullMapOpen(true)}
        />
      </div>
      {isFullMapOpen && (
        <div className="fixed inset-0 z-50 w-full h-full bg-background animate-slide-up">
          <DetailMap
            data={[]}
            center={center}
            mode="full"
            onClose={() => setIsFullMapOpen(false)}
          />
        </div>
      )}
      <div className="grid grid-cols-3 mt-3">
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/parking.svg" width={16} height={16} alt={'주차장'} />
          <p className="font-xs">주차공간</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image
            src="/images/icons/facility/electric.svg"
            width={16}
            height={16}
            alt={'전기차 충전소'}
          />
          <p className="font-xs">전기차 충전소</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/bicycle.svg" width={16} height={16} alt={'따릉이'} />
          <p className="font-xs">따릉이</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/subway.svg" width={16} height={16} alt={'지하철'} />
          <p className="font-xs">지하철</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/bus.svg" width={16} height={16} alt={'버스'} />
          <p className="font-xs">버스</p>
        </div>
      </div>
    </div>
  )
}
