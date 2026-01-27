import type { FacilityItem } from '@/shared/types/map-types'
import { getSubwayColor } from '@/shared/utils/map-helpers'
interface DetailMapCardProps {
  item: FacilityItem
  onClose?: () => void
}

export default function DetailMapCard({ item, onClose }: DetailMapCardProps) {
  const isParking = item.category === 'PARKING'
  const isSubway = item.category === 'SUBWAY'
  const isBus = item.category === 'BUS'
  const isFree = item.tags?.includes('무료')
  const subwayLineName = item.line
  const subwayColor = isSubway ? getSubwayColor(subwayLineName) : '#999A98'

  const displayLineName =
    subwayLineName && /^\d+$/.test(subwayLineName) ? `${subwayLineName}호선` : subwayLineName
  return (
    <article
      className="w-full h-[160px] bg-white rounded-2xl shadow-md p-5 animate-slide-up cursor-default relative"
      onClick={(e) => e.stopPropagation()}
    >
      {/* 닫기 버튼 */}
      {onClose && (
        <button
          onClick={(e) => {
            e.stopPropagation()
            onClose()
          }}
          className="absolute top-4 right-4 p-1 text-sub hover:text-default transition-colors z-10"
          aria-label="닫기"
        >
          <span className="text-xl font-medium">✕</span>
        </button>
      )}

      {/* Header: 이름 + 태그 */}
      <div className="flex items-baseline flex-wrap gap-2 mb-1 w-full pr-8">
        {/* Title: 16px SemiBold (#383938) */}
        <h3 className="text-body-1-sb text-default break-keep">{item.name}</h3>

        {/* [Tag Spec 반영]
           - Spec: Body2/B (14px, Bold 700)
           - Color: Gray 500 (#999A98)
           - Code: text-[14px] font-bold text-gray-500
        */}
        {isParking && isFree && (
          <span className="text-[14px] leading-[1.5] font-bold text-gray-500">무료</span>
        )}

        {/* 지하철 태그 (컬러는 동적, 폰트는 Bold 적용) */}
        {isSubway && displayLineName && (
          <span
            className="text-[14px] leading-[1.5] font-bold whitespace-nowrap"
            style={{ color: subwayColor }}
          >
            {displayLineName}
          </span>
        )}
        {/* 버스 태그 (Gray 500, Bold) */}
        {isBus && (
          <span className="text-[14px] leading-[1.5] font-bold text-gray-500">{item.id}</span>
        )}
      </div>

      {/* Body: 주소 */}
      {/* Spec: Caption1/M (13px, Medium), #70716F */}
      {item.address && (
        <p className="text-caption-1-m text-sub-deep mb-4 break-keep">{item.address}</p>
      )}

      {/* Footer: 주차장 전용 현황판 */}
      {isParking && (
        <div className="flex flex-col gap-1">
          {item.updatedAt && (
            <span className="text-caption-2-r text-sub-bright text-right">
              {item.updatedAt} 기준
            </span>
          )}

          {/* 주차 현황 박스 */}
          <div className="w-full bg-gray-100 rounded-lg py-3 px-4 flex justify-between items-center">
            {/* [Label Spec 반영]
               - Spec: Caption1/SB (13px, SemiBold 600)
               - Color: Sub-Deep (#70716F)
               - Code: text-caption-1-sb text-sub-deep
            */}
            <span className="text-caption-1-sb text-sub-deep">잔여 주차공간 현황</span>

            {/* 숫자 영역 */}
            <div className="flex items-baseline gap-0.5">
              {/* 잔여: 13px SemiBold, Gray 700 (#515251) */}
              <span className="text-caption-1-sb text-gray-700">{item.availableSpots ?? '-'}</span>

              {/* 구분선: Gray 500 */}
              <span className="text-caption-1-m text-gray-500">/</span>

              {/* 전체: 13px Medium, Gray 500 (#999A98) */}
              <span className="text-caption-1-m text-gray-500">{item.totalSpots ?? '-'}</span>
            </div>
          </div>
        </div>
      )}
    </article>
  )
}
