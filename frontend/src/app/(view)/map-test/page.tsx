'use client'

import { useState } from 'react'
import KakaoMap from '@/components/common/KakaoMap'
import { BaseMapItem } from '@/shared/types/map-types'

interface TestPlace extends BaseMapItem {
  address: string
  category: string
}

const TEST_DATA: TestPlace[] = [
  {
    id: 1,
    name: '서울숲',
    lat: 37.5444,
    lng: 127.0374,
    address: '서울 성동구 뚝섬로 273',
    category: '공원',
  },
  {
    id: 2,
    name: '올림픽공원',
    lat: 37.5207,
    lng: 127.1215,
    address: '서울 송파구 올림픽로 424',
    category: '공원',
  },
  {
    id: 3,
    name: '여의도한강공원',
    lat: 37.5284,
    lng: 126.9331,
    address: '서울 영등포구 여의동로 330',
    category: '공원',
  },
]

export default function Page() {
  // 초기 중심 좌표 (서울 시청)
  const [center] = useState({ lat: 37.5665, lng: 126.978 })

  return (
    <main className="w-full h-[100dvh] relative bg-default">
      <KakaoMap<TestPlace>
        data={TEST_DATA}
        center={center}
        getMarkerImage={(item) => '/images/icons/marker.svg'}
        // 마커 클릭 시 나올 카드 디자인
        renderCard={(item) => (
          <div className="flex flex-col gap-xs">
            {/* 상단 타이틀 및 배지 */}
            <div className="flex items-center justify-between">
              <h3 className="text-title-sb text-default">{item.name}</h3>
            </div>

            {/* 주소 */}
            <p className="text-body-2-r text-sub">{item.address}</p>

            {/* 카테고리 태그 */}
            <div className="mt-1">
              <span className="text-caption-1-r text-primary bg-primary-pale px-2 py-1 rounded-sm">
                #{item.category}
              </span>
            </div>
          </div>
        )}
        // 카드 클릭 이벤트
        onCardClick={(item) => {
          alert(`'${item.name}' 상세 페이지로 이동합니다! (ID: ${item.id})`)
        }}
      />
    </main>
  )
}
