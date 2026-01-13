'use client'

import Image from 'next/image'
import EmblaCarousel from '@/components/common/EmblaCarousel'

interface ParkThumbnailProps {
  data?: string[]
  height?: number | string
  dotBottom?: number | string
}

export default function ParkThumbnail({
  data = [],
  height = 400,
  dotBottom = 48,
}: ParkThumbnailProps) {
  return (
    <EmblaCarousel
      showDots
      height={height}
      dotBottom={dotBottom}
    >
      {data.map((src, i) => (
        <div key={i} className="relative w-full h-full">
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