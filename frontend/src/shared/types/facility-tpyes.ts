export interface TransitResponse {
  areaCode: string
  areaName: string
  subwayStations: SubwayStation[]
  busStations: BusStation[]
  sbikes: SBikeStation[]
}

export interface SubwayStation {
  subId: number
  subStnName: string
  subStnLine: string
  roadAddr: string
  subStnX: number
  subStnY: number
}

export interface BusStation {
  busStnId: number
  busArsId: number
  busStnName: string
  busStnX: number
  busStnY: number
}

export interface SBikeStation {
  sbikeSpotId: string
  sbikeSpotName: string
  sbikeCapacity: number
  sbikeX: number
  sbikeY: number
}

export interface ParkingResponse {
  chargerStations: ChargerStation[]
  parkingLots: ParkingLot[]
}

export interface ChargerStation {
  stationId: string
  stationName: string
  stationAddr: string
  stationX: number
  stationY: number
  stationUsetime: string
  stationParkpay: boolean
  stationKindDetail: string
  stationLimitDetail: string
  chargerDetails: ChargerDetail[]
}

export interface ChargerDetail {
  chargerId: number
  chargerType: string
  chargerUpdated: string
  output: number
  method: string
  chargerStatus: string
  statusUpdated: string
}

export interface ParkingLot {
  prkCode: number
  prkName: string
  prkType: string
  capacity: number
  currentInfoYn: boolean
  currentPrkCnt: number
  currentPrkTime: string
  payYn: boolean
  addr: string
  roadAddr: string
  prkX: number
  prkY: number
}