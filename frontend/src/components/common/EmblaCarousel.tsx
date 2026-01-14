'use client'

import useEmblaCarousel from 'embla-carousel-react'
import { DotButton, useDotButton } from '@/components/common/EmblaDot'
import { ReactNode } from 'react'

interface EmblaCarouselProps {
  children: ReactNode[]
  height?: number | string
  showDots?: boolean
  dotBottom?: number | string
}

export default function EmblaCarousel({
  children,
  height = 400,
  showDots = false,
  dotBottom = 16,
}: EmblaCarouselProps) {
  const [emblaRef, emblaApi] = useEmblaCarousel()

  const dotState = showDots ? useDotButton(emblaApi) : null

  return (
    <div className="embla">
      {/* viewport */}
      <div className="overflow-hidden relative" ref={emblaRef}>
        {/* container */}
        <div className="flex">
          {children.map((child, i) => (
            <div
              key={i}
              className="flex-[0_0_100%] min-w-0"
              style={{ height }}
            >
              {child}
            </div>
          ))}
        </div>

        {/* dots (옵션) */}
        {showDots && dotState && (
          <div
            className="absolute left-1/2 -translate-x-1/2 flex gap-3"
            style={{ bottom: dotBottom }}
          >
            {dotState.scrollSnaps.map((_, index) => (
              <DotButton
                key={index}
                onClick={() => dotState.onDotButtonClick(index)}
                className={[
                  'w-[8px] h-[8px] rounded-full',
                  index === dotState.selectedIndex
                    ? 'bg-white'
                    : 'bg-white opacity-50',
                ].join(' ')}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  )
}