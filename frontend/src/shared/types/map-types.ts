export type CongestionLevel = '여유' | '보통' | '약간 붐빔' | '붐빔'

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
  category: 'PARKING' | 'SUBWAY' | 'BIKE'
  detailInfo: string
}
// 실제 사용 통합 타입
export type MapDataType = ParkItem | FacilityItem
