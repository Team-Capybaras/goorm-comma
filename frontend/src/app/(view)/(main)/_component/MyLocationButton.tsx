'use client'

import Image from 'next/image'
import { useEffect, useState } from 'react'
import { useLocationStore } from '@/store/location.store'
import { useGlobalToast } from '@/components/common/ToastProvider'

export default function MyLocationButton() {
  const { loading, locationName, fetchCurrentLocation } = useLocationStore()
  const showToast = useGlobalToast()

  // 같은 결과여도 useEffect 실행을 위한 트리거
  const [clickId, setClickId] = useState(0)

  const handleClick = () => {
    setClickId(prev => prev + 1)
    fetchCurrentLocation()
  }

  useEffect(() => {
    if (loading) return
    if (!locationName) return

    if (locationName === '서울특별시 · 중구') {
      showToast(
        '현위치를 알 수 없어서 기본 위치로 설정했어요',
        'default',
        3000
      )
    } else {
      showToast('현위치로 설정했어요', 'default', 3000)
    }
  }, [clickId, loading, locationName])

  return (
    <button
      onClick={handleClick}
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