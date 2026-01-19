import {FacilityItem} from "@/shared/types/map-types";
import {ParkingResponse, TransitResponse} from "@/shared/types/facility-tpyes";

export function mapTransitToFacilityItems(
  data: TransitResponse,
): FacilityItem[] {
  return [
    ...data.subwayStations.map<FacilityItem>((s) => ({
      id: `SUBWAY-${s.subId}`,
      category: 'SUBWAY',
      name: s.subStnName,
      lat: s.subStnY,
      lng: s.subStnX,
      address: s.roadAddr,
      line: s.subStnLine,
      tags: ['지하철'],
    })),

    ...data.busStations.map<FacilityItem>((b) => ({
      id: `BUS-${b.busStnId}`,
      category: 'BUS',
      name: b.busStnName,
      lat: b.busStnY,
      lng: b.busStnX,
      tags: ['버스'],
    })),

    ...data.sbikes.map<FacilityItem>((s) => ({
      id: `BIKE-${s.sbikeSpotId}`,
      category: 'BIKE',
      name: s.sbikeSpotName,
      lat: s.sbikeY,
      lng: s.sbikeX,
      totalSpots: s.sbikeCapacity,
      tags: ['따릉이'],
    })),
  ]
}


export function mapParkingToFacilityItems(
  data: ParkingResponse,
): FacilityItem[] {
  const parkingLots = data.parkingLots.map<FacilityItem>((p) => ({
    id: `PARKING-${p.prkCode}`,
    category: 'PARKING',
    name: p.prkName,
    lat: p.prkY,
    lng: p.prkX,
    address: p.roadAddr || p.addr,
    totalSpots: p.capacity,
    availableSpots: p.currentInfoYn
      ? p.capacity - p.currentPrkCnt
      : undefined,
    updatedAt: p.currentPrkTime,
    tags: ['주차장'],
  }))

  const chargers = data.chargerStations.map<FacilityItem>((c) => ({
    id: `EV-${c.stationId}`,
    category: 'EV_CHARGER',
    name: c.stationName,
    lat: c.stationY,
    lng: c.stationX,
    address: c.stationAddr,
    tags: ['전기차'],
  }))

  return [...parkingLots, ...chargers]
}