import {CongestionLevel} from "@/shared/utils/congestion-helper";

export interface ParkList {
  areaName: string
  areaCode: string
  latitude: number
  longitude: number
}

export type ParkType = 'COURSE' | 'FACILITY' | 'NEARBY'

export interface ParkFeatures {
  type: ParkType
  description: string
}

export interface ParkListWithPage {
  hasNext: boolean
  nextCursor: string
  size: number
  parks: ParkInfo[]
  totalCount: number
  count: number
}

export interface ParkInfo {
  areaCode: string
  areaName: string
  images: string[]
  temp: number
  address: string
  distance: number
  precptMsg?: string
  airIndex: string
  areaCongestLevel: CongestionLevel
  features: ParkFeatures[]
  recommendedVisitHour: string
  tags: string[]
  longitude: number
  latitude: number
}