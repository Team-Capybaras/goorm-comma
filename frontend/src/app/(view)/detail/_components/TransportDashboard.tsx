'use client'

import DetailMap from '@/components/map/DetailMap'
import { FacilityItem, MapDataType } from '@/shared/types/map-types'
import Image from 'next/image'
import { use, useState } from 'react'

const center = {
  lat: 37.5444,
  lng: 127.0374,
}
const ALL_CATEGORY_DATA: MapDataType[] = [
  {
    id: 'main-park',
    category: 'PARK',
    name: '서울숲 (메인)',
    lat: 37.5444,
    lng: 127.0374,
    areaCode: 'POI001',
    congestion: '여유',
  },
  {
    id: 'parking-1',
    category: 'PARKING',
    name: '서울숲 공영주차장',
    lat: 37.5455,
    lng: 127.0385,
    detailInfo: '5분 150원 | 총 211면 | 24시간 운영',
  },
  {
    id: 'subway-1',
    category: 'SUBWAY',
    name: '서울숲역 3번 출구',
    lat: 37.543,
    lng: 127.044,
    detailInfo: '수인분당선 | 에스컬레이터 있음',
  },
  {
    id: 'bike-1',
    category: 'BIKE',
    name: '따릉이 대여소 (입구)',
    lat: 37.544,
    lng: 127.039,
    detailInfo: '대여 가능: 15대 | QR형 뉴따릉이',
  },
  {
    id: 'bus-1',
    category: 'BUS',
    name: '뚝섬역 8번출구 정류장',
    lat: 37.546,
    lng: 127.04,
    detailInfo: '간선 121, 지선 2014, 2224 정차',
  },
  {
    id: 'ev-1',
    category: 'EV_CHARGER',
    name: '서울숲 급속 충전소',
    lat: 37.5452,
    lng: 127.0382,
    detailInfo: '급속 2기 | 완속 3기 | 현재 대기 없음',
  },
]

export default function TransportDashboard({ data }: any) {
  const [isFullMapOpen, setIsFullMapOpen] = useState(false)
  return (
    <div className="mt s-6 mb-6">
      <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
      <div className="w-full h-60 mt-3 rounded-xl overflow-hidden">
        <DetailMap
          data={ALL_CATEGORY_DATA as FacilityItem[]}
          center={center}
          mode="preview"
          onExpand={() => setIsFullMapOpen(true)}
        />
      </div>
      {isFullMapOpen && (
        <div className="fixed inset-0 z-50 w-full h-full bg-background animate-slide-up">
          <DetailMap
            data={ALL_CATEGORY_DATA as FacilityItem[]}
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
