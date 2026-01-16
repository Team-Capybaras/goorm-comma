'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import Image from 'next/image'
import KakaoMap from '@/components/common/KakaoMap'
import ParkMapCard from '@/components/map/ParkMapCard'
import FloatingBar from '@/components/common/FloatingBar'
import type { ParkItem } from '@/shared/types/map-types'
import { getCongestionMarkerIcon } from '@/shared/utils/map-helpers'

interface Props {
  data: ParkItem[]
  center: { lat: number; lng: number }
}

export default function MainMap({ data, center }: Props) {
  const router = useRouter()
  const [map, setMap] = useState<any>(null)
  const [isLocLoading, setIsLocLoading] = useState(false)
  const [selectedPark, setSelectedPark] = useState<ParkItem | null>(null)

  const handleCurrentLocation = () => {
    if (!map) return // 지도가 로드되지 않았으면 중단

    setIsLocLoading(true) // 로딩 시작

    // 브라우저 내장 API로 현재 좌표 가져오기
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude
        const lng = position.coords.longitude

        // 카카오맵 좌표 객체 생성
        const locPosition = new window.kakao.maps.LatLng(lat, lng)

        map.panTo(locPosition)
        setIsLocLoading(false) // 로딩 끝
      },
      (err) => {
        console.error(err)
        setIsLocLoading(false)
      }
    )
  }

  const handleCardClick = (item: ParkItem) => {
    router.push(`/detail/${item.id}`)
  }

  return (
    <div className="w-full h-full relative">
      <KakaoMap<ParkItem>
        data={data}
        center={center}
        level={7} // 구 단위가 보이는 적절한 줌 레벨
        getMarkerImage={(item, _isSelected) => getCongestionMarkerIcon(item.congestion)}
        markerSize={{ width: 32, height: 32 }}
        activeMarkerSize={{ width: 48, height: 48 }}
        selectedItem={selectedPark}
        setSelectedItem={setSelectedPark}
        renderCard={(item) => <ParkMapCard item={item} />}
        onMapLoad={(loadedMap) => setMap(loadedMap)}
        onCardClick={handleCardClick}
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
