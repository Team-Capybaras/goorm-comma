export type CongestionLevel = '여유' | '보통' | '약간 붐빔' | '붐빔'

export type FacilityCategory =
  | 'PARKING' // 주차장
  | 'SUBWAY' // 지하철
  | 'BIKE' // 따릉이
  | 'BUS' // 버스정류장
  | 'EV_CHARGER' // 전기차 충전소

export interface BaseMapItem {
  id: string | number
  lat: number
  lng: number
  name: string
}

export interface ParkItem extends BaseMapItem {
  category: 'PARK'
  areaCode: string
  congestion: CongestionLevel
}

export interface FacilityItem extends BaseMapItem {
  category: FacilityCategory
  detailInfo: string
}
// 실제 사용 통합 타입
export type MapDataType = ParkItem | FacilityItem
