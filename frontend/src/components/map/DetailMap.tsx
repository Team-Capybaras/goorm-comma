'use client'

import { useState } from 'react'
import Image from 'next/image'
import KakaoMap from '@/components/common/KakaoMap'
import type { FacilityItem } from '@/shared/types/map-types'
import DetailMapCard from '@/components/map/DetailMapCard'
import { getFacilityMarkerIcon } from '@/shared/utils/map-helpers'

interface Props {
  data: FacilityItem[]
  center: { lat: number; lng: number }
  mode?: 'preview' | 'full'
  onClose?: () => void
  onExpand?: () => void
}

export default function DetailMap({ data, center, mode = 'full', onClose, onExpand }: Props) {
  const [map, setMap] = useState<any>(null)
  const [selectedItem, setSelectedItem] = useState<FacilityItem | null>(null)

  const handleButtonClick = (e: React.MouseEvent) => {
    e.stopPropagation()

    if (mode === 'preview') {
      onExpand?.()
    } else {
      onClose?.()
    }
  }

  return (
    <div className="w-full h-full relative bg-background overflow-hidden group">
      <KakaoMap<FacilityItem>
        data={data}
        center={center}
        level={mode === 'preview' ? 4 : 3}
        getMarkerImage={(item, isSelected) => getFacilityMarkerIcon(item.category, isSelected)}
        markerSize={{ width: 64, height: 64 }}
        activeMarkerSize={{ width: 80, height: 80 }}
        selectedItem={selectedItem}
        setSelectedItem={setSelectedItem}
        renderCard={(item) =>
          mode === 'full' ? (
            <DetailMapCard item={item} onClose={() => setSelectedItem(null)} />
          ) : null
        }
        showLabel={false}
        onCardClick={mode === 'full' ? undefined : () => {}}
        onMapLoad={(loadedMap) => {
          setMap(loadedMap)
          if (mode === 'preview' && loadedMap) {
            loadedMap.setDraggable(true)
            loadedMap.setZoomable(false)
          }
        }}
      />

      {(!selectedItem || mode !== 'full') && (
        <button
          onClick={handleButtonClick}
          className="absolute top-4 right-4 z-20 w-[40px] h-[40px] bg-white rounded-full shadow-md flex items-center justify-center hover:bg-gray-50 transition-colors"
          aria-label={mode === 'preview' ? '지도 확대' : '지도 축소'}
        >
          <Image
            src={mode === 'preview' ? '/images/icons/maximize.svg' : '/images/icons/minimize.svg'}
            alt={mode === 'preview' ? '확대' : '축소'}
            width={24}
            height={24}
          />
        </button>
      )}
    </div>
  )
}
