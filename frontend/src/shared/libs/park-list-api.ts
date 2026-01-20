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
  const url = '/v1/parks'

  const res = await api.get(url, {
    params: {
      tag_names: activeOptions,
      size: 10,
      cursor: pageParam,
      sort: activeSort,
      latitude: location.lat,
      longitude: location.lng,
    },
  })

  return res.data.data
}