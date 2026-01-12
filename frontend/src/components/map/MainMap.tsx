'use client'

import KakaoMap from '@/components/common/KakaoMap'
import ParkMapCard from '@/components/map/ParkMapCard'
import type { ParkItem } from '@/shared/types/map-types'
import { getCongestionMarkerIcon } from '@/shared/utils/map-helpers'

interface Props {
  data: ParkItem[]
  center: { lat: number; lng: number }
}

export default function MainMap({ data, center }: Props) {
  return (
    <div className="w-full h-full relative">
      <KakaoMap<ParkItem>
        data={data}
        center={center}
        level={7} // 구 단위가 보이는 적절한 줌 레벨
        //혼잡도별 마커 이미지
        getMarkerImage={(item) => getCongestionMarkerIcon(item.congestion)}
        //카드 렌더링
        renderCard={(item) => <ParkMapCard item={item} />}
      />
    </div>
  )
}
