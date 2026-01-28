'use client'

import { useEffect, useRef, useState } from 'react'
import type { BaseMapItem } from '@/shared/types/map-types'
import { useLocationStore } from '@/store/location.store'

interface KakaoMapProps<T extends BaseMapItem> {
  data: T[]
  center: { lat: number; lng: number }
  level?: number
  getMarkerImage: (item: T, isSelected: boolean) => string
  renderCard: (item: T) => React.ReactNode
  onCardClick?: (item: T) => void
  onMapLoad?: (map: any) => void

  markerSize: { width: number; height: number }
  activeMarkerSize: { width: number; height: number }

  selectedItem?: T | null
  setSelectedItem?: (item: T | null) => void
  showLabel?: boolean
}

export default function KakaoMap<T extends BaseMapItem>({
  data,
  center,
  level = 7,
  getMarkerImage,
  renderCard,
  onCardClick,
  onMapLoad,
  markerSize,
  activeMarkerSize,
  selectedItem: cardSelectedItem,
  setSelectedItem: cardSetSelectedItem,
  showLabel = true,
}: KakaoMapProps<T>) {
  const mapContainer = useRef<HTMLDivElement>(null)
  const [mapInstance, setMapInstance] = useState<any>(null)
  const markersMapRef = useRef<Map<string | number, any>>(new Map())
  const overlaysMapRef = useRef<Map<string | number, any>>(new Map())
  const myLocationMarkerRef = useRef<any>(null)
  const [selectedItem, setSelectedItem] = useState<T | null>(null)
  const { location } = useLocationStore()

  useEffect(() => {
    if (cardSelectedItem !== undefined) {
      setSelectedItem(cardSelectedItem)
    }
  }, [cardSelectedItem])

  const handleInternalSelect = (item: T | null) => {
    setSelectedItem(item)
    cardSetSelectedItem?.(item)
  }

  // 1. 지도 초기화
  useEffect(() => {
    if (typeof window === 'undefined') return

    const initMap = () => {
      if (!window.kakao || !window.kakao.maps) {
        setTimeout(initMap, 100)
        return
      }

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

        window.kakao.maps.event.addListener(map, 'click', () => {
          handleInternalSelect(null)
        })
      })
    }
    initMap()
  }, [])

  // 2. 현재 위치 표시
  useEffect(() => {
    if (!mapInstance || !window.kakao) return
    if (!location) return

    const myPosition = new window.kakao.maps.LatLng(location.lat, location.lng)
    if (myLocationMarkerRef.current) {
      myLocationMarkerRef.current.setMap(null)
    }

    const imageSrc = '/images/icons/map/current-location-dot.svg'
    const imageSize = new window.kakao.maps.Size(48, 48)
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize)

    const marker = new window.kakao.maps.Marker({
      position: myPosition,
      image: markerImage,
      zIndex: 20,
      map: mapInstance,
    })

    myLocationMarkerRef.current = marker
  }, [mapInstance])

  // 3. 실시간 위치 추적
  useEffect(() => {
    if (!mapInstance || !navigator.geolocation) return
    const watchId = navigator.geolocation.watchPosition((pos) => {
      const { latitude, longitude } = pos.coords
      const latlng = new window.kakao.maps.LatLng(latitude, longitude)
      if (myLocationMarkerRef.current) {
        myLocationMarkerRef.current.setPosition(latlng)
      }
    })
    return () => navigator.geolocation.clearWatch(watchId)
  }, [mapInstance])

  // 4. 마커 및 오버레이 생성
  useEffect(() => {
    if (!mapInstance || !window.kakao) return

    markersMapRef.current.forEach((marker) => marker.setMap(null))
    markersMapRef.current.clear()
    overlaysMapRef.current.forEach((overlay) => overlay.setMap(null))
    overlaysMapRef.current.clear()

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
        <div class="translate-y-[-5px]"> 
          <div class="bg-white/90 backdrop-blur-sm px-2 py-1 rounded-xl shadow-sm ">
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

      if (showLabel) {
        customOverlay.setMap(mapInstance)
      }
      overlaysMapRef.current.set(item.id, customOverlay)

      window.kakao.maps.event.addListener(marker, 'click', () => {
        handleInternalSelect(item)
        mapInstance.panTo(markerPosition)
      })

      marker.setMap(mapInstance)
      markersMapRef.current.set(item.id, marker)
    })
  }, [data, mapInstance, getMarkerImage, markerSize])

  // 5. 라벨 토글 전용 이펙트
  useEffect(() => {
    if (!mapInstance) return

    overlaysMapRef.current.forEach((overlay) => {
      overlay.setMap(showLabel ? mapInstance : null)
    })
  }, [showLabel, mapInstance])

  // 6. 선택된 마커 스타일 변경
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

  // 7. 중심 이동
  useEffect(() => {
    if (mapInstance && center) {
      const moveLatLon = new window.kakao.maps.LatLng(center.lat, center.lng)

      mapInstance.panTo(moveLatLon)
      mapInstance.setLevel(level, { animate: { duration: 300 } })
    }
  }, [center, level, mapInstance])

  return (
    <div className="relative w-full h-full overflow-hidden bg-background">
      <div ref={mapContainer} className="w-full h-full" />

      {selectedItem && (
        <>
          <div className="absolute bottom-0 left-0 w-full z-20 animate-slide-up">
            <div onClick={() => onCardClick?.(selectedItem)}>{renderCard(selectedItem)}</div>
          </div>
        </>
      )}
    </div>
  )
}
