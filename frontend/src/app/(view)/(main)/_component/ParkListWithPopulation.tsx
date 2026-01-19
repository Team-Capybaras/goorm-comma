'use client'

import {useLocationStore} from "@/store/location.store";
import ParkListContent from "@/app/(view)/(main)/_component/ParkListContent";

export default function ParkListWithPopulation() {

  const { location } = useLocationStore()

  if (!location) {
    return null
  }

  return (
    <div className="flex bg-bright justify-center mb-3">
      <div className="w-full max-w-2xl flex flex-col gap-4 p-5">
        <div className="flex items-center justify-start">
          <p className="text-caption-1-m text-sub">현재 인구 흐름을 분석해 혼잡도를 표시해요.</p>
          <img
            src="/images/icons/info.svg"
            alt="정보"
            width={16}
            height={16}
            className="ml-2"
          />
        </div>
        <ParkListContent location={location} />
      </div>
    </div>
  )
}