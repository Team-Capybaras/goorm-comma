'use client'

import DetailMap from '@/components/map/DetailMap'
import { FacilityItem } from '@/shared/types/map-types'

const center = { lat: 37.5444, lng: 127.0374 }
const ALL_CATEGORY_DATA: FacilityItem[] = [
  {
    id: 'main-park',
    name: '서울숲 (메인)',
    lat: 37.5444,
    lng: 127.0374,
    category: 'BUS',
    tags: ['무료'],
  },
  {
    id: 'parking-1',
    category: 'PARKING',
    name: '서울숲 공영주차장',
    lat: 37.5455,
    lng: 127.0385,
    totalSpots: 211,
    availableSpots: 33,
    updatedAt: '18:30',
    tags: ['무료'],
    address: '서울 성동구 성수동1가 685-63',
  },
]

export default function FullMapPage() {
  return (
    <div className="w-full h-[100dvh]">
      <DetailMap data={ALL_CATEGORY_DATA} center={center} mode="full" />
    </div>
  )
}
