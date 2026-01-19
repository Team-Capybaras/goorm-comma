import { api } from './axios'
import type { ParkItem, CongestionLevel } from '@/shared/types/map-types'

interface ParkListResponse {
  data: {
    parks: Array<{
      areaCode: string
      areaName: string
      latitude: number
      longitude: number
      areaCongestLevel: string
      distance: number
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

export const getParkList = async (lat: number, lng: number): Promise<ParkItem[]> => {
  const res = await api.get<ParkListResponse>('/v1/parks', {
    params: {
      latitude: lat,
      longitude: lng,
      size: 100,
    },
  })

  const parks = res.data?.data?.parks || []

  const isValidCongestion = (level: string): level is CongestionLevel => {
    return ['여유', '보통', '약간 붐빔', '붐빔'].includes(level)
  }

  return parks.map((park) => ({
    id: park.areaCode,
    category: 'PARK',
    name: park.areaName,
    lat: park.latitude,
    lng: park.longitude,
    areaCode: park.areaCode,
    congestion: isValidCongestion(park.areaCongestLevel)
      ? (park.areaCongestLevel as CongestionLevel)
      : '보통',
    distance: `${park.distance}km`,

    weather: { iconStatus: '', text: '' },
    airQuality: '',
    image: '',
    tags: [],
    forecast: '',
    isDetail: false,
  }))
}

export const getParkDetail = async (
  areaCode: string,
  lat: number,
  lng: number
): Promise<ParkItem> => {
  try {
    const res = await api.get<ParkDetailResponse>(`/v1/parks/${areaCode}`, {
      params: {
        latitude: lat,
        longitude: lng,
      },
    })

    const info = res.data?.data?.park

    if (!info) {
      console.warn(`[API] ${areaCode} 상세 정보 없음, 기본값 반환`)
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
        airQuality: '-',
        image: '',
        tags: [],
        forecast: '',
        isDetail: true,
      }
    }

    const msg = info.precptMsg || ''
    let iconStatus = '맑음'

    if (msg.includes('눈') && !msg.includes('없')) {
      iconStatus = '눈'
    } else if (msg.includes('비') && !msg.includes('없')) {
      iconStatus = '비'
    }

    return {
      id: info.areaCode,
      category: 'PARK',
      name: info.areaName,
      lat: info.latitude,
      lng: info.longitude,
      areaCode: info.areaCode,
      congestion: info.areaCongestLevel as CongestionLevel,
      distance: `${info.distance}km`,
      weather: {
        iconStatus: iconStatus,
        text: info.temp !== undefined ? `${info.temp}℃` : '',
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
