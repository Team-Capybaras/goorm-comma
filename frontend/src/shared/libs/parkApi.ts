import { api } from './axios'
import type { ParkItem, CongestionLevel } from '@/shared/types/map-types'

interface ParkListResponse {
  data: {
    parks: Array<{
      areaCode: string
      areaName: string
      latitude: number
      longitude: number
      temp: number
      precptMsg: string
      airIndex: string
      areaCongestLevel: string
      distance: number
      images: string[]
      tags: string[]
      recommendedVisitHour: string
    }>
  }
}
interface ParkDetailResponse {
  data: {
    park: {
      areaCode: string
      areaName: string
      latitude: number
      longitude: number
      areaCongestLevel: string
      distance: number
      temp: number
      precptMsg: string | null
      airIndex: string
      images: string[]
      tags: string[] | null
      recommendedVisitHour: string
    } | null
  }
}

const isValidCongestion = (level: string): level is CongestionLevel => {
  return ['여유', '보통', '약간 붐빔', '붐빔'].includes(level)
}

const getWeatherIconStatus = (msg: string | null) => {
  const message = msg || ''
  if (message.includes('눈') && !message.includes('없')) return '눈'
  if (message.includes('비') && !message.includes('없')) return '비'
  return '맑음'
}

export const getParkList = async (lat: number, lng: number): Promise<ParkItem[]> => {
  const res = await api.get<ParkListResponse>('/v1/parks', {
    params: {
      latitude: lat,
      longitude: lng,
      size: 100,
    },
  })

  const parks = res.data?.data?.parks || []

  return parks.map((park) => {
    const iconStatus = getWeatherIconStatus(park.precptMsg)

    return {
      id: park.areaCode,
      category: 'PARK',
      name: park.areaName,
      lat: park.latitude,
      lng: park.longitude,
      areaCode: park.areaCode,

      // 혼잡도 연결
      congestion: isValidCongestion(park.areaCongestLevel)
        ? (park.areaCongestLevel as CongestionLevel)
        : '보통',

      distance: `${park.distance}km`,

      weather: {
        iconStatus: iconStatus,
        text: `${park.temp}℃`,
      },
      airQuality: park.airIndex || '-',
      image: park.images?.[0] || '',
      tags: park.tags || [],
      forecast: park.recommendedVisitHour || '',
      isDetail: true,
    }
  })
}

export const getParkDetail = async (
  areaCode: string,
  lat: number,
  lng: number
): Promise<ParkItem> => {
  try {
    const res = await api.get<ParkDetailResponse>(`/v1/parks/${areaCode}`, {
      params: { latitude: lat, longitude: lng },
    })

    const info = res.data?.data?.park

    if (!info) {
      return {
        id: areaCode,
        category: 'PARK',
        name: '정보 없음',
        lat: lat,
        lng: lng,
        areaCode: areaCode,
        congestion: '보통',
        distance: '',
        weather: { iconStatus: '맑음', text: '-' },
        isDetail: true,
      } as ParkItem
    }

    const iconStatus = getWeatherIconStatus(info.precptMsg)

    return {
      id: info.areaCode,
      category: 'PARK',
      name: info.areaName,
      lat: info.latitude,
      lng: info.longitude,
      areaCode: info.areaCode,
      congestion: isValidCongestion(info.areaCongestLevel)
        ? (info.areaCongestLevel as CongestionLevel)
        : '보통',
      distance: `${info.distance}km`,
      weather: {
        iconStatus: iconStatus,
        text: `${info.temp}℃`,
      },
      airQuality: info.airIndex || '-',
      image: info.images?.[0] || '',
      tags: info.tags || [],
      forecast: info.recommendedVisitHour || '',
      isDetail: true,
    }
  } catch (e) {
    console.error(`[API Error] 상세 조회 실패 (${areaCode})`, e)
    throw e
  }
}
