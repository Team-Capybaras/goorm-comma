'use client'

import Image from 'next/image'
import { useLocation } from '@/providers/LocationProvider'

export default function MyLocationButton() {
  const { locationName, loading, getCurrentLocation } = useLocation()

  return (
    <button
      onClick={getCurrentLocation}
      disabled={loading}
      className="inline-flex items-center gap-1 text-caption-1-m text-sub"
    >
      <Image
        src="/images/icons/current.svg"
        alt="현재 위치"
        width={18}
        height={18}
      />
      {loading ? '위치 찾는 중…' : locationName ?? '현재 위치'}
    </button>
  )
}