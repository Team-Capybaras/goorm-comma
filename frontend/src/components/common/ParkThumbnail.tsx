'use client'

import Image from 'next/image'
import EmblaCarousel from '@/components/common/EmblaCarousel'

interface ParkThumbnailProps {
  data?: string[]
  height?: number | string
  dotBottom?: number | string
  showDots?: boolean
  layout?: 'cover' | 'peek'
}

export default function ParkThumbnail({
  data = [],
  height = 400,
  dotBottom = 48,
  showDots = true,
  layout = 'cover',
}: ParkThumbnailProps) {
  const isPeek = layout === 'peek'

  return (
    <EmblaCarousel
      showDots={showDots}
      height={height}
      dotBottom={dotBottom}
      slideClassName={
        isPeek
          ? 'flex-[0_0_80%] pr-3'
          : 'flex-[0_0_100%]'
      }
    >
      {data.map((src, i) => (
        <div key={i} className={`relative w-full h-full
          ${isPeek ? 'border rounded-8 overflow-hidden' : ''}`}
        >
          <Image
            src={src}
            alt={`park-thumbnail-${i}`}
            fill
            className="object-cover"
            priority={i === 0}
          />
        </div>
      ))}
    </EmblaCarousel>
  )
}