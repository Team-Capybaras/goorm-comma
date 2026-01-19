'use client'

import Image from 'next/image'
import ParkFilter from "@/app/(view)/_component/ParkFilter";
import ParkCard from "@/app/(view)/_component/ParkCard";
import {api} from "@/shared/libs/axios";
import {useEffect, useState} from "react";
import {ParkListWithPage} from "@/shared/types/park-types";
import {useLocationStore} from "@/store/location.store";

export default function ParkListWithPopulation() {
  const [activeOptions, setActiveOptions] = useState<string[]>([])
  const [data, setData] = useState<ParkListWithPage>()
  const [loading, setLoading] = useState(false)
  const { location, initLocation  } = useLocationStore()

  useEffect(() => {
    const tagUrl = '/v1/parks/by-tags'
    const distanceUrl = '/v1/parks/by-distance'
    const congestionUrl = '/v1/parks/low-congestion'

    const url = activeOptions.length > 0 ? tagUrl : distanceUrl

    const fetchData = async () => {
      setLoading(true)

      try {
        const res = await api.get(url, {
          params: {
            tag_names: activeOptions,
            sizes: 10,
            longitude: location?.lng,
            latitude: location?.lat
          },
        })
        setData(res.data.data)
      } finally {
        setLoading(false)
      }
    }

    if(location?.lat && location.lng) {
      fetchData()
    }
  }, [activeOptions, location])

  return (
    <div className="flex bg-bright justify-center mb-3">
      <div className="w-full max-w-2xl flex flex-col gap-4 p-5">
        <div className="flex items-center justify-start">
          <p className="text-caption-1-m text-sub">현재 인구 흐름을 분석해 혼잡도를 표시해요.</p>
          <Image
            src="/images/icons/info.svg"
            alt="정보"
            width={16}
            height={16}
            className="ml-2"
          />
        </div>
        {/* filter */}
        <ParkFilter activeOptions={activeOptions} setActiveOptions={setActiveOptions}/>
        {/* card */}
        {(data && data.parks.length > 0) ?
          <ParkCard data={data} /> :
          (
            <div className="flex justify-center items-center p-5 h-70 w-full">
              <p>조건에 맞는 공원이 없어요.
                필터를 다시 설정해보세요</p>
            </div>
          )
        }
      </div>
    </div>
  )
}