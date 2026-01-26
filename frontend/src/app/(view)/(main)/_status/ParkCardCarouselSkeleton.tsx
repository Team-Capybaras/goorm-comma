'use client'

import EmblaCarousel from '@/components/common/EmblaCarousel'

export default function ParkCardCarouselSkeleton() {
  return (
    <EmblaCarousel showDots={false} containerClassName="mr-5">
      {[0, 1, 2].map((i) => (
        <div
          key={i}
          className={`relative mx-1 flex-[0_0_80%] rounded-8 overflow-hidden animate-pulse ${
            i === 0 && 'ml-5'
          }`}
          style={{ height: 362 }}
        >
          {/* 이미지 영역 */}
          <div className="absolute inset-0 bg-gray-200" />

          {/* 그라데이션 */}
          <div className="absolute inset-0 bg-gradient-to-t from-black/20 via-transparent to-transparent" />

          {/* 상단 추천 시간 */}
          <div className="absolute top-4 right-4 z-10">
            <div className="flex items-center gap-1 px-3 py-1 rounded-full bg-black/20">
              <div className="w-4 h-4 bg-gray-300 rounded" />
              <div className="w-[40px] h-[12px] bg-gray-300 rounded" />
            </div>
          </div>

          {/* 하단 정보 */}
          <div className="absolute bottom-4 left-4 right-4 z-10 text-white">
            {/* 혼잡도 */}
            <div className="mb-2">
              <div className="w-[48px] h-[26px] rounded-8 bg-gray-300" />
            </div>

            {/* 공원명 + 거리 */}
            <div className="flex items-end gap-2 mb-2">
              <div className="h-[20px] w-[120px] bg-gray-300 rounded" />
              <div className="h-[14px] w-[40px] bg-gray-300 rounded" />
            </div>

            {/* 태그 */}
            <div className="flex gap-1">
              <div className="h-[14px] w-[40px] bg-gray-300 rounded" />
              <div className="h-[14px] w-[48px] bg-gray-300 rounded" />
              <div className="h-[14px] w-[36px] bg-gray-300 rounded" />
            </div>
          </div>
        </div>
      ))}
    </EmblaCarousel>
  )
}