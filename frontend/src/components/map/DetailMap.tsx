'use client'

import KakaoMap from '@/components/common/KakaoMap'
import type { MapDataType } from '@/shared/types/map-types'

interface Props {
  data: MapDataType[]
  center: { lat: number; lng: number }
}

export default function DetailMap({ data, center }: Props) {
  return (
    <section className="w-full h-full relative mt-4 rounded-xl overflow-hidden shadow-sm">
      <KakaoMap<MapDataType>
        data={data}
        center={center}
        level={4}
        // 카테고리별 마커 이미지 연결
        getMarkerImage={(item) => {
          switch (item.category) {
            case 'PARK':
              return '/images/icons/marker.svg'
            case 'PARKING':
              return '/images/icons/marker.svg'
            case 'SUBWAY':
              return '/images/icons/marker.svg'
            case 'BIKE':
              return '/images/icons/marker.svg'
            case 'BUS':
              return '/images/icons/marker.svg'
            case 'EV_CHARGER':
              return '/images/icons/marker.svg'
            default:
              return '/images/icons/marker.svg'
          }
        }}
        // 마커 클릭 시 보여줄 하단 카드 디자인
        renderCard={(item) => {
          // 메인 공원일 때
          if (item.category === 'PARK') {
            return (
              <div className="p-2 text-center">
                <h3 className="text-body-1-sb text-primary">{item.name}</h3>
                <p className="text-caption-1-m text-sub">현재 보고 계신 장소입니다</p>
              </div>
            )
          }

          // 주변 편의시설일 때 (주차장, 지하철 등)
          return (
            <div className="flex flex-col gap-xs p-1">
              <div className="flex items-center gap-sm">
                {/* 카테고리 뱃지 */}
                <Badge category={item.category} />
                <h3 className="text-body-1-sb text-default">{item.name}</h3>
              </div>

              {/* 상세 정보 (API에서 받은 detailInfo 문자열 출력) */}
              <div className="p-3 bg-background-deep rounded-md mt-2">
                <p className="text-caption-1-r text-sub-deep break-keep">{item.detailInfo}</p>
              </div>
            </div>
          )
        }}
      />
    </section>
  )
}

// 뱃지 컴포넌트 (내부에서만 쓰니까 여기에 정의)
function Badge({ category }: { category: string }) {
  let label = '기타'
  let colorClass = 'bg-sub text-white'

  switch (category) {
    case 'PARKING':
      label = '주차장'
      colorClass = 'bg-deep text-white'
      break
    case 'SUBWAY':
      label = '지하철'
      colorClass = 'bg-orange-500 text-white'
      break
    case 'BIKE':
      label = '따릉이'
      colorClass = 'bg-green-600 text-white'
      break
    case 'BUS':
      label = '버스'
      colorClass = 'bg-blue-600 text-white'
      break
    case 'EV_CHARGER':
      label = '충전소'
      colorClass = 'bg-blue-400 text-white'
      break
  }

  return <span className={`${colorClass} text-caption-2-b px-2 py-0.5 rounded-xs`}>{label}</span>
}
