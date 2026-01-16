'use client'

import useEmblaCarousel from 'embla-carousel-react'
import { DotButton, useDotButton } from '@/components/common/EmblaDot'
import { ReactNode } from 'react'
import cn from '@/shared/utils/cn'

interface EmblaCarouselProps {
  children: ReactNode[]
  height?: number | string
  showDots?: boolean
  dotBottom?: number | string
  slideClassName?: string
  viewportClassName?: string
}

export default function EmblaCarousel({
  children,
  height = 400,
  showDots = false,
  dotBottom = 16,
  slideClassName,
  viewportClassName,
}: EmblaCarouselProps) {
  const [emblaRef, emblaApi] = useEmblaCarousel()

  const dotState = showDots ? useDotButton(emblaApi) : null

  return (
    <div className="embla">
      {/* viewport */}
      <div
        ref={emblaRef}
        className={cn(
          'relative overflow-hidden',
          viewportClassName
        )}
      >
        {/* container */}
        <div className="flex">
          {children.map((child, i) => (
            <div
              key={i}
              className={cn(
                'min-w-0 flex-[0_0_100%]',
                slideClassName
              )}
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
                className={cn(
                  'w-[8px] h-[8px] rounded-full',
                  index === dotState.selectedIndex
                    ? 'bg-white'
                    : 'bg-white opacity-50',
                )}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  )
}