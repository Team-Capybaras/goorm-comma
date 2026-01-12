'use client'

import { useEffect, useRef, useState } from 'react'
import type { BaseMapItem } from '@/shared/types/map-types'
import Image from 'next/image'
interface KakaoMapProps<T extends BaseMapItem> {
  data: T[] // 지도에 뿌릴 데이터 목록
  center: { lat: number; lng: number } // 지도 중심 좌표
  level?: number // 확대 레벨 (기본값 7)
  getMarkerImage: (item: T) => string
  renderCard: (item: T) => React.ReactNode
  onCardClick?: (item: T) => void
}

export default function KakaoMap<T extends BaseMapItem>({
  data,
  center,
  level = 7,
  getMarkerImage,
  renderCard,
  onCardClick,
}: KakaoMapProps<T>) {
  const mapContainer = useRef<HTMLDivElement>(null)
  const [mapInstance, setMapInstance] = useState<any>(null)
  const markersRef = useRef<any[]>([]) // 마커들을 담아둘 배열
  const [selectedItem, setSelectedItem] = useState<T | null>(null)
  const [isLoading, setIsLoading] = useState(false)
  //현위치 이동 핸들러 함수
  const handleCurrentLocation = () => {
    if (!mapInstance) return // 지도가 아직 로드 안됐으면 중단

    setIsLoading(true) // 로딩 시작 (아이콘 뺑글뺑글)

    // 브라우저 내장 API로 현재 좌표 가져오기
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const lat = position.coords.latitude
        const lng = position.coords.longitude

        // 카카오맵 좌표 객체 생성
        const locPosition = new window.kakao.maps.LatLng(lat, lng)

        // 지도 중심 부드럽게 이동
        mapInstance.panTo(locPosition)
        setIsLoading(false) // 로딩 끝
      },
      (err) => {
        console.error(err)
        setIsLoading(false)
      }
    )
  }
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

      // 지도 빈 곳 클릭 시 선택 해제
      window.kakao.maps.event.addListener(map, 'click', () => {
        setSelectedItem(null)
      })
    })
  }, []) // 의존성 배열 비움
  // 2. 데이터가 바뀌면 마커만 새로 그리기
  useEffect(() => {
    if (!mapInstance || !window.kakao) return

    // 기존 마커 싹 지우기
    markersRef.current.forEach((marker) => marker.setMap(null))
    markersRef.current = []

    // 새 마커 생성
    data.forEach((item) => {
      const imageSrc = getMarkerImage(item)
      const imageSize = new window.kakao.maps.Size(24, 35)
      const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize)
      const markerPosition = new window.kakao.maps.LatLng(item.lat, item.lng)

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
        title: item.name,
        image: markerImage,
      })

      // 마커 클릭 이벤트
      window.kakao.maps.event.addListener(marker, 'click', () => {
        setSelectedItem(item)
        mapInstance.panTo(markerPosition) // 클릭한 곳으로 이동
      })

      marker.setMap(mapInstance)
      markersRef.current.push(marker) // 배열에 저장
    })
  }, [data, mapInstance, getMarkerImage])

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

      {/* 현위치 버튼: 이미지 아이콘 사용 & 테마 적용 */}
      <button
        onClick={handleCurrentLocation}
        className={`absolute left-4 z-30 bg-background border border-border p-2 rounded-lg shadow-md hover:bg-muted transition-all duration-300 ease-in-out ${
          selectedItem ? 'bottom-52' : 'bottom-6'
        }`}
        aria-label="내 위치로 이동"
      >
        {/* SVG 이미지 적용 (로딩 시 회전) */}
        <Image
          src="/images/icons/current.svg"
          alt="현위치"
          width={24}
          height={24}
          className={`size-6 ${isLoading ? 'animate-spin' : ''}`}
        />
      </button>
      {selectedItem && (
        <div className="absolute bottom-6 left-4 right-4 z-20 animate-slide-up">
          <div onClick={() => onCardClick?.(selectedItem)}>{renderCard(selectedItem)}</div>
        </div>
      )}
    </div>
  )
}
