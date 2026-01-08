import { CongestionLevel } from '@/shared/types/map-types'

//날씨 상태 문자열에 따라 아이콘 경로 반환
export const getWeatherIconPath = (status: string): string => {
  const s = status?.trim() || ''

  // API 데이터나 텍스트에 포함된 키워드로 아이콘 매칭
  if (s.includes('맑음') || s.includes('해')) return '/images/icons/weather/sun.svg'
  if (s.includes('비')) return '/images/icons/weather/rain.svg'
  if (s.includes('눈')) return '/images/icons/weather/snow.svg'
  if (s.includes('구름') || s.includes('흐림')) return '/images/icons/weather/cloud.svg'
  if (s.includes('구름') && s.includes('해')) return '/images/icons/weather/cloudsun.svg'

  // 기본값
  return '/images/icons/weather/sun.svg'
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

//혼잡도 레벨에 따라 마커 아이콘 경로를 반환
export const getCongestionMarkerIcon = (level: CongestionLevel): string => {
  /* // 이미지 준비시 주석 해제
  switch (level) {
    case '여유': return '/images/icons/marker-green.svg';
    case '보통': return '/images/icons/marker-yellow.svg';
    case '약간 붐빔': return '/images/icons/marker-orange.svg';
    case '붐빔': return '/images/icons/marker-red.svg';
  }
  */
  return '/images/icons/marker.svg'
}
