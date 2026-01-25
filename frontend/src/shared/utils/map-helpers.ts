import { CongestionLevel, FacilityCategory } from '@/shared/types/map-types'

//날씨 상태 문자열에 따라 아이콘 경로 반환
export const getWeatherIconPath = (status: string): string => {
  const s = status?.trim() || ''

  // API 데이터나 텍스트에 포함된 키워드로 아이콘 매칭
  if (s.includes('맑음') || s.includes('해')) return '/images/icons/weather/sun.svg'
  if (s.includes('비')) return '/images/icons/weather/umbrella-gray.svg'
  if (s.includes('눈')) return '/images/icons/weather/snow.svg'
  if (s.includes('구름') || s.includes('흐림')) return '/images/icons/weather/cloud.svg'
  if (s.includes('구름') && s.includes('해')) return '/images/icons/weather/cloudsun.svg'

  // 기본값
  return '/images/icons/map/map-sun.svg'
}

//혼잡도 레벨에 따라 텍스트 색상 클래스 반환
export const getCongestionColorClass = (level: CongestionLevel): string => {
  switch (level) {
    case '여유':
      return 'text-positive' // 초록색
    case '보통':
      return 'text-normal' // 노란색
    case '약간 붐빔':
      return 'text-caution' // 주황색
    case '붐빔':
      return 'text-warning' // 빨간색
    default:
      return 'text-normal'
  }
}

export const getCongestionColorValue = (level: CongestionLevel): string => {
  switch (level) {
    case '여유':
      return 'rgb(var(--positive))' // 초록
    case '보통':
      return 'rgb(var(--normal))' // 노랑
    case '약간 붐빔':
      return 'rgb(var(--caution))' // 주황
    case '붐빔':
      return 'rgb(var(--warning))' // 빨강
    default:
      return 'rgb(var(--normal))'
  }
}

//혼잡도 레벨에 따라 마커 아이콘 경로를 반환
export const getCongestionMarkerIcon = (level: CongestionLevel): string => {
  switch (level) {
    case '여유':
      return '/images/icons/map/state=positive.svg'
    case '보통':
      return '/images/icons/map/state=normal.svg'
    case '약간 붐빔':
      return '/images/icons/map/state=caution.svg'
    case '붐빔':
      return '/images/icons/map/state=warning.svg'
  }

  return '/images/icons/map/state=normal.svg'
}

const FACILITY_ICON_MAP: Record<FacilityCategory, string> = {
  PARKING: 'parking',
  SUBWAY: 'subway',
  BIKE: 'bicycle',
  BUS: 'bus',
  EV_CHARGER: 'charge',
}

export const getFacilityMarkerIcon = (category: FacilityCategory, isSelected: boolean): string => {
  const typeStr = FACILITY_ICON_MAP[category] || 'bus'

  const stateStr = isSelected ? 'focused' : 'Default'

  return `/images/icons/map/state=${stateStr}_type=${typeStr}.svg`
}

//지하철 노선별 색상 매핑
const SUBWAY_COLORS: Record<string, string> = {
  '1호선': '#004A85',
  '2호선': '#00A23F',
  '3호선': '#ED6C00',
  '4호선': '#009BCE',
  '5호선': '#794698',
  '6호선': '#7C4932',
  '7호선': '#6E7E31',
  '8호선': '#D11D70',
  '9호선': '#A49D87',
  우이신설선: '#BACC50',
  신림선: '#5E7DBB',
  수인분당: '#ECA300',
  신분당: '#B81B30',
  공항철도: '#0079AC',
}

// 기본 색상
const DEFAULT_SUBWAY_COLOR = '#999A98'

export const getSubwayColor = (tags: string[] = []): string => {
  if (!tags || tags.length === 0) return DEFAULT_SUBWAY_COLOR

  // 태그 중 '호선'이나 '선'이 포함된 문자열 찾기
  const lineName = tags.find((t) => t.includes('호선') || t.includes('선'))

  if (!lineName) return DEFAULT_SUBWAY_COLOR

  // 정확한 매칭 확인
  for (const [key, color] of Object.entries(SUBWAY_COLORS)) {
    if (lineName.includes(key)) return color
  }

  return DEFAULT_SUBWAY_COLOR
}
