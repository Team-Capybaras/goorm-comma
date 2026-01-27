import type { FacilityItem } from '@/shared/types/map-types'
import { getSubwayColor } from '@/shared/utils/map-helpers'
import { CardCloseButton } from '@/components/common/CardCloseButton'

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
      className="w-[calc(100%-48px)] mx-auto min-w-[300px] h-[160px] bg-white rounded-2xl shadow-md p-5 animate-slide-up cursor-default relative mb-6 !overflow-visible"
      onClick={(e) => e.stopPropagation()}
    >
      {onClose && (
        <div className="absolute -top-[52px] right-0 z-50">
          <CardCloseButton onClose={onClose} />
        </div>
      )}

      {/* Header: 이름 + 태그 */}
      <div className="flex items-baseline flex-wrap gap-2 mb-1 w-full pr-8">
        <h3 className="text-body-1-sb text-default break-keep">{item.name}</h3>

        {isParking && isFree && (
          <span className="text-[14px] leading-[1.5] font-bold text-gray-500">무료</span>
        )}

        {isSubway && displayLineName && (
          <span
            className="text-[14px] leading-[1.5] font-bold whitespace-nowrap"
            style={{ color: subwayColor }}
          >
            {displayLineName}
          </span>
        )}

        {isBus && (
          <span className="text-[14px] leading-[1.5] font-bold text-gray-500">{item.id}</span>
        )}
      </div>

      {/* Body: 주소 */}
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

          <div className="w-full bg-gray-100 rounded-lg py-3 px-4 flex justify-between items-center">
            <span className="text-caption-1-sb text-sub-deep">잔여 주차공간 현황</span>

            <div className="flex items-baseline gap-0.5">
              <span className="text-caption-1-sb text-gray-700">{item.availableSpots ?? '-'}</span>
              <span className="text-caption-1-m text-gray-500">/</span>
              <span className="text-caption-1-m text-gray-500">{item.totalSpots ?? '-'}</span>
            </div>
          </div>
        </div>
      )}
    </article>
  )
}
