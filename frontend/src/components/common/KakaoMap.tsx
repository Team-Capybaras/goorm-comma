'use client'

import { useEffect, useRef, useState } from 'react'
import { useRouter } from 'next/navigation'
import type { ParkData, CongestionLevel } from '@/shared/types/congestion'
import { Card, CardHeader, CardContent } from '@/components/common/Card'
import { Locate } from 'lucide-react'

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
  const router = useRouter()

  const [mapInstance, setMapInstance] = useState<any>(null)
  const [isLoading, setIsLoading] = useState(false)
  const [parks, setParks] = useState<ParkData[]>([])
  const [selectedPark, setSelectedPark] = useState<ParkData | null>(null)

  // 데이터 로딩
  useEffect(() => {
    // 실제 API 호출로 대체 예정
    setParks(MOCK_DATA)
  }, [])

  const handleCardClick = (parkName: string) => {
    router.push('')
  }

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

  // 혼잡도 텍스트 색상
  const getCongestionColor = (level: CongestionLevel) => {
    switch (level) {
      case '여유':
        return 'text-blue-500'
      case '보통':
        return 'text-green-500'
      case '약간 붐빔':
        return 'text-orange-500'
      case '붐빔':
        return 'text-red-500'
      default:
        return 'text-gray-500'
    }
  }
  //지도 그리기 및 마커 표시
  useEffect(() => {
    if (typeof window === 'undefined' || !window.kakao || !window.kakao.maps) return

    // autoload=false 옵션을 썼으므로 load 함수로 감싸서 실행
    window.kakao.maps.load(() => {
      const options = {
        center: new window.kakao.maps.LatLng(37.515, 126.995), // 지도의 중심좌표
        level: 7, // 확대 레벨
      }

      // 지도 생성
      if (mapContainer.current) {
        mapContainer.current.innerHTML = '' // 지도 초기화
        // 지도 생성
        const map = new window.kakao.maps.Map(mapContainer.current, options)

        setMapInstance(map)
        // 지도 빈 공간 클릭 시 카드 닫기
        window.kakao.maps.event.addListener(map, 'click', function () {
          setSelectedPark(null)
        })
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
            title: park.name,
            image: markerImage, // 커스텀 이미지
          })

          // 클릭 이벤트 리스너
          window.kakao.maps.event.addListener(marker, 'click', function () {
            setSelectedPark(park)
            const moveLatLon = new window.kakao.maps.LatLng(park.lat, park.lng)
            map.setLevel(7, { animate: true })
            map.panTo(moveLatLon)
          })

          // 지도에 마커 올리기
          marker.setMap(map)
        })
      }
    })
  }, [parks])

  return (
    // 부모 요소의 크기를 따라가도록 100% 설정
    <div className="relative w-full h-full overflow-hidden">
      {/* 지도 영역 */}
      <div ref={mapContainer} className="w-full h-full" />

      <button
        onClick={handleCurrentLocation}
        className={`absolute left-4 z-30 bg-white p-2 rounded-lg shadow-md hover:bg-gray-100 transition-all duration-300 ease-in-out ${
          selectedPark ? 'bottom-42' : 'bottom-6'
        }`}
        aria-label="내 위치로 이동"
      >
        <Locate
          className={`size-6 ${isLoading ? 'animate-spin text-blue-500' : 'text-gray-700'}`}
        />
      </button>

      {/* 카드 영역 (selectedPark가 있을 때만 표시) */}
      {selectedPark && (
        <div className="absolute bottom-6 left-4 right-4 z-20 animate-slide-up">
          <Card
            // 카드를 클릭하면 상세페이지로 이동
            onClick={() => handleCardClick(selectedPark.name)}
            className="shadow-xl border-none bg-white/95 backdrop-blur-sm cursor-pointer hover:bg-white transition-colors"
          >
            <CardHeader
              closable
              // 닫기 버튼 클릭 시 이벤트 전파(페이지 이동)를 막고 닫기만 수행
              onClose={(e) => {
                e?.stopPropagation()
                setSelectedPark(null)
              }}
              className="pb-2"
            >
              <div className="flex items-center gap-2">
                <span className="text-lg font-bold">{selectedPark.name}</span>
                <span
                  className={`text-sm font-bold ${getCongestionColor(selectedPark.congestion)}`}
                >
                  {selectedPark.congestion}
                </span>
              </div>
            </CardHeader>

            <CardContent>
              <div>상세 정보</div>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  )
}
