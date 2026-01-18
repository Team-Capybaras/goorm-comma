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
      dotBottom={dotBottom}
    >
      {data.map((src, i) => (
        <div
          key={i}
          className={`relative w-full 
          ${isPeek ? 'border rounded-8 overflow-hidden flex-[0_0_80%] pr-3' : 'flex-[0_0_100%]'}`}
          style={{ height }}
        >
          <Image
            src={src}
            alt={`park-thumbnail-${i}`}
            fill
            sizes="100vw"
            className="object-cover"
            priority={i === 0}
          />
        </div>
      ))}
    </EmblaCarousel>
  )
}