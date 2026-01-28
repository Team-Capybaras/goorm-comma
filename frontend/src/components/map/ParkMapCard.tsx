'use client'

import Image from 'next/image'
import MapCard from '@/components/map/MapCard'
import type { ParkItem } from '@/shared/types/map-types'
import { getCongestionColorClass, getWeatherIconPath } from '@/shared/utils/map-helpers'
import Tag from '@/components/common/Tag'
import { CardCloseButton } from '@/components/common/CardCloseButton'

interface Props {
  item: ParkItem
  onClose?: () => void
}

const airQualityTextMap: Record<string, string> = {
  나쁨: '대기질이 나빠요',
  보통: '대기질이 보통이에요',
  좋음: '대기질이 좋아요',
  점검중: '아직 정보가 없어요',
}

export default function ParkMapCard({ item, onClose }: Props) {
  return (
    <MapCard
      congestion={item.congestion}
      className="w-[calc(100%-48px)] mx-auto min-w-[300px] cursor-pointer mb-10 relative !overflow-visible"
    >
      {onClose && (
        <div className="absolute -top-[52px] right-0 z-50">
          <CardCloseButton onClose={onClose} />
        </div>
      )}

      <div className="flex justify-between items-start gap-3">
        {/* 좌측 텍스트 컨테이너 */}
        <div className="flex flex-col gap-1 flex-1 min-w-0">
          {/* 이름 + 거리 */}
          <div className="flex items-baseline gap-1.5 mb-0.5">
            <h3 className="text-body-1-sb text-default leading-none">{item.name}</h3>
            <span className="text-body-2-m text-sub leading-none">{item.distance}</span>
          </div>

          {/* 혼잡도 + 날씨 아이콘/텍스트 */}
          <div className="flex items-center gap-1 text-body-2-sb">
            <span className={getCongestionColorClass(item.congestion)}>{item.congestion}</span>
            <div className="w-0.75 h-0.75 rounded-full bg-deep"></div>
            <div className="flex items-center gap-1">
              <img
                src={getWeatherIconPath(item.weather.iconStatus)}
                alt="날씨"
                width={14}
                height={14}
              />
              <span className="text-body-2-r text-sub">{item.weather.text}</span>
            </div>
          </div>

          {/* 대기질 정보는 나쁨일 때만 표시 */}
          {item.airQuality === '나쁨' && (
            <div className="my-[-2] flex items-center gap-1 text-body-2-r text-sub">
              {/* 구름 아이콘 재사용 또는 대기질 전용 아이콘 사용 */}
              <img src="/images/icons/weather/air.svg" alt="대기질" width={14} height={14} />
              <span>{airQualityTextMap[item.airQuality]}</span>
            </div>
          )}
        </div>

        {/* 우측 썸네일 이미지 (데이터가 있을 경우만 표시) */}
        {item.image && (
          <div className="relative w-[72px] h-[72px] shrink-0 rounded-lg overflow-hidden bg-background-deep">
            <Image src={item.image} alt={item.name} sizes="72px" fill className="object-cover" />
          </div>
        )}
      </div>

      <div className="flex flex-wrap items-center gap-1.5 mt-3">
        {/* 예측 배지 */}
        {item.forecast && <Tag variant="blue">{item.forecast}</Tag>}

        {/* 태그 목록 */}
        {item.tags?.map((tag, index) => (
          <Tag key={index}>{tag}</Tag>
        ))}
      </div>
    </MapCard>
  )
}
