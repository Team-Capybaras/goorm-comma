'use client'

import { useState } from 'react'
import KakaoMap from '@/components/common/KakaoMap'
import { MapDataType } from '@/shared/types/map-types'
import DetailMap from '@/components/map/DetailMap'

const TEST_CENTER = { lat: 37.5444, lng: 127.0374 }

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

export default function Page() {
  const [center] = useState({ lat: 37.5665, lng: 126.978 })

  return (
    <main className="w-full h-[100dvh] relative bg-default">
      {/* DetailMap 컴포넌트 사용
         - getMarkerImage, renderCard 로직이 이미 DetailMap 내부에 구현되어 있습니다.
         - 데이터만 넘겨주면 알아서 마커 아이콘과 카드를 구분해서 그려줍니다.
      */}
      <DetailMap data={ALL_CATEGORY_DATA} center={center} />
    </main>
  )
}
