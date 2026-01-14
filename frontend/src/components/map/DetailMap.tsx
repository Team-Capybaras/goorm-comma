'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import Image from 'next/image'

// 공통 컴포넌트 & 타입
import KakaoMap from '@/components/common/KakaoMap'
import type { FacilityItem } from '@/shared/types/map-types'

// 컴포넌트 & 헬퍼
import DetailMapCard from '@/components/map/DetailMapCard'
import { getFacilityMarkerIcon } from '@/shared/utils/map-helpers'

interface Props {
  data: FacilityItem[]
  center: { lat: number; lng: number }
  /**
   * preview: 대시보드 내 작은 지도 (확대 버튼, 조작 불가)
   * full: 전체 화면 지도 (축소 버튼, 조작 가능, 카드 노출)
   * @default 'full'
   */
  mode?: 'preview' | 'full'
}

export default function DetailMap({ data, center, mode = 'full' }: Props) {
  const router = useRouter()
  const [map, setMap] = useState<any>(null)

  const handleButtonClick = (e: React.MouseEvent) => {
    e.stopPropagation()

    if (mode === 'preview') {
      router.push('/detail/map')
    } else {
      router.back()
    }
  }

  return (
    <div className="w-full h-full relative bg-background overflow-hidden group">
      {mode === 'preview' && (
        <div
          onClick={() => router.push('/detail/map')}
          className="absolute inset-0 z-10 cursor-pointer bg-transparent"
        />
      )}

      <KakaoMap<FacilityItem>
        data={data}
        center={center}
        level={mode === 'preview' ? 6 : 4}
        getMarkerImage={(item, isSelected) => getFacilityMarkerIcon(item.category, isSelected)}
        markerSize={{ width: 24, height: 24 }}
        activeMarkerSize={{ width: 32, height: 32 }}
        renderCard={(item) => (mode === 'full' ? <DetailMapCard item={item} /> : null)}
        onCardClick={mode === 'full' ? undefined : () => {}}
        onMapLoad={(loadedMap) => {
          setMap(loadedMap)
        }}
      />

      <button
        onClick={handleButtonClick}
        className="absolute top-4 right-4 z-20 w-[40px] h-[40px] bg-white rounded-full shadow-md flex items-center justify-center hover:bg-gray-50 transition-colors"
        aria-label={mode === 'preview' ? '지도 확대' : '지도 축소'}
      >
        <Image
          // 모드에 따라 아이콘 변경
          src={
            mode === 'preview' ? '/images/icons/map/maximize.svg' : '/images/icons/map/minimize.svg'
          }
          alt={mode === 'preview' ? '확대' : '축소'}
          width={24}
          height={24}
        />
      </button>
    </div>
  )
}
