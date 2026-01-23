'use client'

import Image from 'next/image'
import { Card } from '@/components/common/Card'
import EmblaCarousel from '@/components/common/EmblaCarousel'
import cn from '@/shared/utils/cn'
import { ParkInfo } from '@/shared/types/park-types'
import { CONGESTION_COLOR_MAP, CONGESTION_BG_COLOR_MAP } from '@/shared/utils/congestion-helper'
import Link from "next/link";

interface ParkCardCarouselProps {
  data: ParkInfo[]
}

export default function ParkCardCarousel({ data }: ParkCardCarouselProps) {
  return (
    <EmblaCarousel showDots={false} containerClassName={'mr-5'}>
      {/* ParkInfo를 API에서 불러올 데이터로 셋 */}
      {data.map((park,i) => (
        // key 값에 따른 공원 정보 생성
        <Link
          key={park.areaName}
          href={`/detail/${park.areaCode}`}
          className={`relative mx-1 flex-[0_0_80%] rounded-8 overflow-hidden ${(i ===0) && 'ml-5'}`}
          style={{ height: 362 }}
        >
          {/* 공원 이미지 */}
          <Image
            src={park.images[0]}
            alt={park.areaName}
            fill
            className="object-cover"
          />

          {/* 가독성을 위한 그라데이션 효과 */}
          <div className="absolute inset-0 bg-gradient-to-t from-black/50 via-transparent to-transparent" />

          {/* 상단 info - 추천 시간 */}
          <div className="absolute top-4 right-4 z-10">
            <span className="flex gap-1 px-3 py-0.5 text-caption-2-sb rounded-full bg-black/20 text-white">
              <Image
                src="/images/icons/crowd.svg"
                alt="사람아이콘"
                width={16}
                height={16}
              />{park.recommendedVisitHour}
            </span>
          </div>

          {/* 하단 info */}
          <div className="absolute bottom-4 left-4 right-4 z-10 text-white">
            <div className="flex items-center gap-2 my-1">
              {/* 혼잡도 */}
              <Card
                className={cn(
                  'border-none flex items-center justify-center px-[8px] h-[26px] rounded-8 font-2xs font-semibold',
                  CONGESTION_COLOR_MAP[park.areaCongestLevel],
                  CONGESTION_BG_COLOR_MAP[park.areaCongestLevel],
                )}
              >{park.areaCongestLevel}
              </Card>

            </div>
            <div className="flex items-end gap-2 my-1">
              {/* 공원 이름 */}
              <p className="text-title-2-sb">
                {park.areaName}
              </p>

              {/* 현재 위치에서 거리 */}
              <p className="text-body-2-m mb-[2px]">
                {park.distance}km
              </p>
            </div>

            {/* 태그 */}
            <div className="flex flex-wrap gap-1 text-caption-2-sb text-inverse opacity-70">
              {park.tags.map((tag, idx) => (
                <span key={idx} className="mr-1">
                  #{tag}
                </span>
              ))}
            </div>
          </div>
        </Link>
      ))}
    </EmblaCarousel>
  )
}