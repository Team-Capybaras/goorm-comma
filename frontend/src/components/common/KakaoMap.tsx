'use client'

import { useEffect, useRef, useState } from 'react'
import type { ParkData, CongestionLevel } from '@/shared/types/congestion'

const MOCK_DATA: ParkData[] = [
  { name: '여의도 한강공원', lat: 37.5284, lng: 126.933, congestion: '붐빔' },
  { name: '반포 한강공원', lat: 37.5098, lng: 126.9947, congestion: '약간 붐빔' },
  { name: '뚝섬 한강공원', lat: 37.5291, lng: 127.0695, congestion: '보통' },
  { name: '잠실 한강공원', lat: 37.5178, lng: 127.0859, congestion: '여유' },
  { name: '이촌 한강공원', lat: 37.5172, lng: 126.971, congestion: '여유' },
  { name: '망원 한강공원', lat: 37.555, lng: 126.895, congestion: '보통' },
]

export default function KakaoMap() {
  const mapContainer = useRef<HTMLDivElement>(null)
  const [parks, setParks] = useState<ParkData[]>([])

  // 컴포넌트 실행 시 Mock Data를 State에 넣기
  useEffect(() => {
    setParks(MOCK_DATA)
  }, [])

  // 혼잡도별 마커 이미지 주소 반환 함수 - 추후 디자인 변경 가능
  const getMarkerImage = (level: CongestionLevel) => {
    switch (level) {
      case '여유':
        return 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png'
      case '보통':
        return 'http://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png'
      case '약간 붐빔':
      case '붐빔':
        return 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png'
      default:
        return 'http://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png'
    }
  }
  //지도 그리기 및 마커 표시
  useEffect(() => {
    if (typeof window === 'undefined' || !window.kakao || !window.kakao.maps) {
      console.error('Kakao Maps script is not loaded yet.')
      return
    }

    // autoload=false 옵션을 썼으므로 load 함수로 감싸서 실행
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.515, 126.995), // 지도의 중심좌표
        level: 7, // 확대 레벨
      }

      // 지도 생성
      if (mapContainer.current) {
        // 지도 생성
        const map = new window.kakao.maps.Map(mapContainer.current, options)

        // 저장된 parks 데이터를 반복하며 마커 생성
        parks.forEach((park) => {
          // 이미지 옵션 설정
          const imageSrc = getMarkerImage(park.congestion)
          const imageSize = new window.kakao.maps.Size(24, 35)
          const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize)

          // 마커 위치 설정
          const markerPosition = new window.kakao.maps.LatLng(park.lat, park.lng)

          // 마커 생성
          const marker = new window.kakao.maps.Marker({
            position: markerPosition,
            image: markerImage, // 커스텀 이미지
          })

          // 클릭 이벤트 리스너
          window.kakao.maps.event.addListener(marker, 'click', function () {
            console.log('클릭한 공원:', park.name)
          })

          // 지도에 마커 올리기
          marker.setMap(map)
        })
      }
    })
  }, [parks])

  return (
    // 부모 요소의 크기를 따라가도록 100% 설정
    <div ref={mapContainer} style={{ width: '100%', height: '100%' }} />
  )
}
