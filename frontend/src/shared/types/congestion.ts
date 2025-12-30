export type CongestionLevel = '여유' | '보통' | '약간 붐빔' | '붐빔'

export interface ParkData {
  name: string // 공원 이름
  lat: number // 위도
  lng: number // 경도
  congestion: CongestionLevel // 혼잡도
}
