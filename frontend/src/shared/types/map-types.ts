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
  congestion: CongestionLevel // 👈 string 대신 이걸 쓰세요!
}

export interface FacilityItem extends BaseMapItem {
  category: 'PARKING' | 'SUBWAY' | 'BIKE'
  detailInfo: React.ReactNode
}
