'use client'

import Image from 'next/image'
import {useLocationStore} from "@/store/location.store";

export default function MyLocationButton() {
  const { loading, locationName, fetchCurrentLocation } = useLocationStore()

  return (
    <button
      onClick={fetchCurrentLocation}
      disabled={loading}
      className="inline-flex items-center gap-1 text-caption-1-m text-sub"
    >
      <Image
        src="/images/icons/current.svg"
        alt="현재 위치"
        width={18}
        height={18}
      />
      {loading ? '위치 불러오는 중…' : locationName ?? '현재 위치'}
    </button>
  )
}