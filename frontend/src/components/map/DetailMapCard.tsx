import type { FacilityItem } from '@/shared/types/map-types'

interface FacilityMapCardProps {
  item: FacilityItem
  onClose?: () => void
}

const getSubwayLineColor = (lineName: string): string => {
  if (lineName.includes('1호선')) return '#004A85'
  if (lineName.includes('2호선')) return '#00A23F'
  if (lineName.includes('3호선')) return '#ED6C00'
  if (lineName.includes('4호선')) return '#009BCE'
  if (lineName.includes('5호선')) return '#794698'
  if (lineName.includes('6호선')) return '#7C4932'
  if (lineName.includes('7호선')) return '#6E7E31'
  if (lineName.includes('8호선')) return '#D11D70'
  if (lineName.includes('9호선')) return '#A49D87'
  if (lineName.includes('우이신설선')) return '#BACC50'
  if (lineName.includes('신림선')) return '#5E7DBB'
  if (lineName.includes('수인분당')) return '#ECA300'
  if (lineName.includes('신분당')) return '#B81B30'
  if (lineName.includes('공항철도')) return '#0079AC'
  return '#999A98' // 기본 회색
}

export default function FacilityMapCard({ item, onClose }: FacilityMapCardProps) {
  const isParking = item.category === 'PARKING'
  const isSubway = item.category === 'SUBWAY'
  const isBus = item.category === 'BUS'

  const isFree = item.tags?.includes('무료')
  // 태그에서 '호선'이 포함된 문자열 찾기
  const subwayLine = item.tags?.find((t) => t.includes('호선') || t.includes('선'))
  // 지하철 컬러 결정
  const subwayColor = subwayLine ? getSubwayLineColor(subwayLine) : '#999A98'

  return (
    <article
      className="w-full bg-default rounded-t-xl shadow-[0_-4px_16px_rgba(0,0,0,0.1)] p-5 animate-slide-up cursor-default relative"
      onClick={(e) => e.stopPropagation()}
    >
      {/* 시설 이름 + 카테고리별 뱃지/정보 */}
      <div className="flex justify-between items-start mb-1 gap-2">
        <div className="flex items-center flex-wrap gap-2 pr-6">
          {/* 시설 이름 */}
          <h3 className="text-heading-sb text-default break-keep">{item.name}</h3>

          {/* 주차장 */}
          {isParking && isFree && (
            <span className="inline-flex items-center justify-center px-1.5 py-0.5 rounded-xs text-caption-2-m bg-positive-pale text-positive align-middle">
              무료
            </span>
          )}

          {/* 지하철 */}
          {isSubway && subwayLine && (
            <span
              className="text-caption-1-sb"
              style={{ color: subwayColor }} // 동적 컬러 적용
            >
              {subwayLine}
            </span>
          )}

          {/* 버스*/}
          {isBus && <span className="text-body-2-r text-sub-deep">{item.id}</span>}
        </div>

        {/* 닫기 버튼 */}
        {onClose && (
          <button
            onClick={(e) => {
              e.stopPropagation()
              onClose()
            }}
            className="p-1 -mr-2 -mt-1 text-sub hover:text-default transition-colors shrink-0"
            aria-label="닫기"
          >
            <span className="text-xl font-medium">✕</span>
          </button>
        )}
      </div>

      {/* 주소 */}
      {item.address && <p className="text-body-2-r text-sub mb-5 break-keep">{item.address}</p>}

      {/* 주차장 전용 현황판*/}
      {isParking && (
        <>
          {/* 구분선 */}
          <div className="w-full h-[1px] bg-line-bright mb-4" />

          <div className="flex justify-between items-end">
            <div>
              <span className="text-caption-1-m text-sub-deep block mb-1">잔여 주차공간 현황</span>
              <div className="flex items-baseline gap-1">
                <span className="text-title-1-b text-default">{item.availableSpots ?? '-'}</span>
                <span className="text-body-2-m text-sub-bright">/ {item.totalSpots ?? '-'}</span>
              </div>
            </div>

            {/* 업데이트 시간*/}
            {item.updatedAt && (
              <span className="text-caption-2-r text-sub-bright mb-0.5">{item.updatedAt} 기준</span>
            )}
          </div>
        </>
      )}
    </article>
  )
}
