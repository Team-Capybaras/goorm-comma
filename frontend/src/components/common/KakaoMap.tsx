'use client'

import { useEffect, useRef } from 'react'

export default function KakaoMap() {
  const mapContainer = useRef<HTMLDivElement>(null)

  useEffect(() => {
    // 1. window 객체 및 카카오 스크립트 로드 여부 체크
    if (typeof window === 'undefined' || !window.kakao || !window.kakao.maps) {
      console.error('Kakao Maps script is not loaded yet.')
      return
    }

    // 2. autoload=false 옵션을 썼으므로 load 함수로 감싸서 실행
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3, // 확대 레벨
      }

      // 3. 지도 생성
      if (mapContainer.current) {
        new window.kakao.maps.Map(mapContainer.current, options)
      }
    })
  }, [])

  return (
    // 부모 요소의 크기를 따라가도록 100% 설정
    <div ref={mapContainer} style={{ width: '100%', height: '100%' }} />
  )
}
