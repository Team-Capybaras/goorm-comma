'use client'

import { useEffect, useRef, useState } from 'react'
import type { BaseMapItem } from '@/shared/types/map-types'

interface KakaoMapProps<T extends BaseMapItem> {
  data: T[]
  center: { lat: number; lng: number } // 지도 중심 좌표
  level?: number // 확대 레벨 (기본값 7)
  getMarkerImage: (item: T, isSelected: boolean) => string
  renderCard: (item: T) => React.ReactNode
  onCardClick?: (item: T) => void
  onMapLoad?: (map: any) => void

  markerSize?: { width: number; height: number }
  activeMarkerSize?: { width: number; height: number }
}

export default function KakaoMap<T extends BaseMapItem>({
  data,
  center,
  level = 7,
  getMarkerImage,
  renderCard,
  onCardClick,
  onMapLoad,
  markerSize = { width: 32, height: 32 },
  activeMarkerSize = { width: 48, height: 48 },
}: KakaoMapProps<T>) {
  const mapContainer = useRef<HTMLDivElement>(null)
  const [mapInstance, setMapInstance] = useState<any>(null)

  const markersMapRef = useRef<Map<string | number, any>>(new Map())
  const overlaysMapRef = useRef<Map<string | number, any>>(new Map())

  const [selectedItem, setSelectedItem] = useState<T | null>(null)

  //지도 그리기 및 마커 표시
  useEffect(() => {
    if (typeof window === 'undefined') return

    window.kakao.maps.load(() => {
      if (!mapContainer.current) return

      const options = {
        center: new window.kakao.maps.LatLng(center.lat, center.lng),
        level: level,
      }
      const map = new window.kakao.maps.Map(mapContainer.current, options)
      setMapInstance(map)

      if (onMapLoad) {
        onMapLoad(map)
      }

      // 지도 빈 곳 클릭 시 선택 해제
      window.kakao.maps.event.addListener(map, 'click', () => {
        setSelectedItem(null)
      })
    })
  }, []) // 의존성 배열 비움

  // 데이터가 바뀌면 마커만 새로 그리기
  useEffect(() => {
    if (!mapInstance || !window.kakao) return

    // 기존 마커 및 오버레이 제거
    markersMapRef.current.forEach((marker) => marker.setMap(null))
    markersMapRef.current.clear()
    overlaysMapRef.current.forEach((overlay) => overlay.setMap(null))
    overlaysMapRef.current.clear()

    // 새 마커 생성
    data.forEach((item) => {
      const imageSrc = getMarkerImage(item, false)
      const markerPosition = new window.kakao.maps.LatLng(item.lat, item.lng)

      const imageSize = new window.kakao.maps.Size(markerSize.width, markerSize.height)
      const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize)

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
        title: item.name,
        image: markerImage,
        zIndex: 1,
      })

      const content = `
        <div style="transform: translateY(4px);"> 
          <div class="bg-white/90 backdrop-blur-sm px-2 py-1 rounded-md shadow-sm ">
             <span class="text-[14px] font-semibold text-gray-800 whitespace-nowrap leading-none block">
               ${item.name}
             </span>
          </div>
        </div>
      `
      const customOverlay = new window.kakao.maps.CustomOverlay({
        position: markerPosition,
        content: content,
        yAnchor: 0,
        zIndex: 0,
      })
      // 마커 클릭 이벤트
      window.kakao.maps.event.addListener(marker, 'click', () => {
        setSelectedItem(item)
        mapInstance.panTo(markerPosition)
      })

      marker.setMap(mapInstance)
      customOverlay.setMap(mapInstance)

      markersMapRef.current.set(item.id, marker)
      overlaysMapRef.current.set(item.id, customOverlay)
    })
  }, [data, mapInstance, getMarkerImage, markerSize])

  useEffect(() => {
    if (!mapInstance || !window.kakao) return

    markersMapRef.current.forEach((marker, id) => {
      const isSelected = selectedItem?.id === id
      const item = data.find((d) => d.id === id)
      if (!item) return

      const imageSrc = getMarkerImage(item, isSelected)

      const targetSize = isSelected ? activeMarkerSize : markerSize

      const sizeObj = new window.kakao.maps.Size(targetSize.width, targetSize.height)
      const newMarkerImage = new window.kakao.maps.MarkerImage(imageSrc, sizeObj)

      marker.setImage(newMarkerImage)

      marker.setZIndex(isSelected ? 10 : 1)
    })
  }, [selectedItem, data, getMarkerImage, markerSize, activeMarkerSize])

  // 중심 좌표 이동 (페이지 진입 시 등)
  useEffect(() => {
    if (mapInstance && center) {
      const moveLatLon = new window.kakao.maps.LatLng(center.lat, center.lng)
      mapInstance.setCenter(moveLatLon)
      mapInstance.setLevel(level)
    }
  }, [center, level, mapInstance])

  return (
    //테마 색상 적용 및 부모 크기(h-full) 따르기
    <div className="relative w-full h-full overflow-hidden bg-background">
      <div ref={mapContainer} className="w-full h-full" />

      {selectedItem && (
        <div className="absolute bottom-6 left-4 right-4 z-20 animate-slide-up">
          <div onClick={() => onCardClick?.(selectedItem)}>{renderCard(selectedItem)}</div>
        </div>
      )}
    </div>
  )
}
