'use client'

import useEmblaCarousel from 'embla-carousel-react'
import { DotButton, useDotButton } from '@/components/common/EmblaDot'
import { ReactNode } from 'react'
import cn from '@/shared/utils/cn'

interface EmblaCarouselProps {
  children: ReactNode
  showDots?: boolean
  dotBottom?: number | string
  containerClassName?: string
  viewportClassName?: string
}

export default function EmblaCarousel({
  children,
  showDots = false,
  dotBottom = 16,
  containerClassName,
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
        <div className={cn(`flex`, containerClassName)}>
          {children}
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