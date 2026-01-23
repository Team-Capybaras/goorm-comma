'use client'

import { useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import Image from 'next/image'
import KakaoMap from '@/components/common/KakaoMap'
import ParkMapCard from '@/components/map/ParkMapCard'
import FloatingBar from '@/components/common/FloatingBar'
import type { ParkItem } from '@/shared/types/map-types'
import { getCongestionMarkerIcon } from '@/shared/utils/map-helpers'
import {useLocationStore} from "@/store/location.store";

interface Props {
  data: ParkItem[]
  center: { lat: number; lng: number }
}

export default function MainMap({ data, center }: Props) {
  const router = useRouter()
  const [map, setMap] = useState<any>(null)
  const [isLocLoading, setIsLocLoading] = useState(false)
  const [selectedPark, setSelectedPark] = useState<ParkItem | null>(null)
  const { location, loading: locationLoading } = useLocationStore()

  const [zoomLevel, setZoomLevel] = useState(7)

  const handleCurrentLocation = () => {
    if (!map) return
    setIsLocLoading(true)
    const locPosition = new window.kakao.maps.LatLng(location!.lat, location!.lng)
    map.panTo(locPosition)
    setIsLocLoading(false)
  }

  const handleCardClick = (item: ParkItem) => {
    router.push(`/detail/${item.id}`)
  }

  const handleMapLoad = (loadedMap: any) => {
    setMap(loadedMap)
    setZoomLevel(loadedMap.getLevel()) // 초기 줌 레벨 저장

    window.kakao.maps.event.addListener(loadedMap, 'zoom_changed', () => {
      const level = loadedMap.getLevel()
      setZoomLevel(level)
    })
  }

  const showMarkerName = zoomLevel <= 7

  return (
    <div className="w-full h-full relative">
      <KakaoMap<ParkItem>
        data={data}
        center={center}
        level={7}
        getMarkerImage={(item, _isSelected) => getCongestionMarkerIcon(item.congestion)}
        markerSize={{ width: 48, height: 48 }}
        activeMarkerSize={{ width: 64, height: 64 }}
        selectedItem={selectedPark}
        setSelectedItem={setSelectedPark}
        renderCard={(item) => <ParkMapCard item={item} />}
        onMapLoad={handleMapLoad}
        onCardClick={handleCardClick}
        showLabel={showMarkerName}
      />

      <button
        onClick={handleCurrentLocation}
        className="absolute top-4 left-4 z-30 w-[40px] h-[40px] rounded-full overflow-hidden shadow-md p-0 outline-none hover:opacity-70 transition-opacity"
        aria-label="내 위치로 이동"
      >
        <Image
          src="/images/icons/map/current-button.svg"
          alt="현위치"
          fill
          className={`object-cover scale-200 ${isLocLoading ? 'animate-spin' : ''}`}
        />
      </button>

      {!selectedPark && (
        <div className="absolute bottom-6 left-0 right-0 z-20 flex justify-center pointer-events-none">
          <div className="pointer-events-auto">
            <FloatingBar />
          </div>
        </div>
      )}
    </div>
  )
}
