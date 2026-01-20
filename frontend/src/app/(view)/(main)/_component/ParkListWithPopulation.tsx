'use client'

import { useState } from 'react' // 1. useState 불러오기
import { useLocationStore } from '@/store/location.store'
import ParkListContent from '@/app/(view)/(main)/_component/ParkListContent'
import { CongestionInfoCard } from './CongestionInfoCard'

export default function ParkListWithPopulation() {
  const { location } = useLocationStore()

  const [isInfoOpen, setIsInfoOpen] = useState(false)

  if (!location) {
    return null
  }

  const handleOpenInfo = () => setIsInfoOpen(true)
  const handleCloseInfo = () => setIsInfoOpen(false)

  return (

    <>
      <div className="flex bg-bright justify-center mb-3">
        <div className="w-full max-w-2xl flex flex-col gap-4 p-5">
          <div className="flex items-center justify-start">
            <p className="text-caption-1-m text-sub">현재 인구 흐름을 분석해 혼잡도를 표시해요.</p>

            <button
              type="button"
              onClick={handleOpenInfo}
              className="ml-2 hover:opacity-70 transition-opacity"
              aria-label="혼잡도 정보 보기"
            >
              <img src="/images/icons/info.svg" alt="정보 아이콘" width={16} height={16} />
            </button>
          </div>
          <ParkListContent location={location} />
        </div>
      </div>

      {isInfoOpen && (
        <div className="fixed inset-0 z-[100] flex items-end justify-center px-0">
          <div
            className="absolute inset-0 bg-black/60 backdrop-blur-[2px]"
            onClick={handleCloseInfo}
          />

          <div className="relative z-10 w-full max-w-[480px]">
            <CongestionInfoCard onClose={handleCloseInfo} />
          </div>
        </div>
      )}
    </>
  )
}
