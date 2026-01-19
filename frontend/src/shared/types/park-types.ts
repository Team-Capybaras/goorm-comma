import {CongestionLevel} from "@/shared/utils/congestion-helper";

export interface ParkList {
  areaName: string
  areaCode: string
  latitude: number
  longitude: number
}

export interface ParkInfo {
  areaName: string;
  images: string[];
  temp: number;
  address: string;
  distance: number;
  airIndex: string;
  areaCongestLevel: CongestionLevel;
  recommendedVisitHour: string;
  tags: string[];
  longitude: number;
  latitude: number;
}