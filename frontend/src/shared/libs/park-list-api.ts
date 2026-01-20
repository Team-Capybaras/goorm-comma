import {ParkListWithPage} from "@/shared/types/park-types";
import {api} from "@/shared/libs/axios";

export const fetchParks = async ({
    pageParam = null,
    activeOptions,
    activeSort,
    location,
  }: {
  pageParam?: string | null
  activeOptions: string[]
  activeSort: string
  location: { lat: number; lng: number }
}): Promise<ParkListWithPage> => {
  const tagUrl = '/v1/parks/by-tags'
  const distanceUrl = '/v1/parks/by-distance'
  const congestionUrl = '/v1/parks/low-congestion'

  const isSort= activeSort === 'distance'

  const url = activeOptions.length > 0 ? tagUrl
    : isSort ? distanceUrl : congestionUrl

  const res = await api.get(url, {
    params: {
      tag_names: activeOptions,
      size: 10,
      cursor: pageParam,
      latitude: location.lat,
      longitude: location.lng,
    },
  })

  return res.data.data
}