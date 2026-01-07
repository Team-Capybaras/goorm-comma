'use client'

import Image from "next/image";
import useEmblaCarousel from "embla-carousel-react";
import {DotButton, useDotButton} from "@/components/common/EmblaDot";

interface ParkThumbnailProps {
  data?: string[]
}

export default function ParkThumbnail({data}: ParkThumbnailProps) {
  const [emblaRef, emblaApi] = useEmblaCarousel()

  const { selectedIndex, scrollSnaps, onDotButtonClick } =
    useDotButton(emblaApi)

  return (
    <>
      <div className="embla">
        {/* viewport */}
        <div className="overflow-hidden relative" ref={emblaRef}>
          {/*container*/}
          <div className="flex">
            {data?.map((src, i) => (
              /* slide */
              <div className="flex-[0_0_100%] min-w-0 h-[260px]" key={i}>
                <div className="relative w-full h-full">
                  <Image
                    src={src}
                    alt={`park-thumbnail-${i}`}
                    fill
                    className="object-cover"
                    priority={i === 0}
                  />
                </div>
              </div>
            ))}
          </div>
          {/* dots */}
          <div className="absolute left-1/2 bottom-2.5 -translate-x-1/2 flex flex-wrap justify-center items-center gap-1.5">
            {scrollSnaps.map((_, index) => (
              <DotButton
                key={index}
                onClick={() => onDotButtonClick(index)}
                className={[
                  'flex justify-center items-center w-5 h-5',
                  'after:content-[""] after:w-2 after:h-2 after:rounded-full after:cursor-pointer',
                  index === selectedIndex
                    ? 'after:bg-white'
                    : 'after:bg-neutral-800',
                ].join(' ')}
              />
            ))}
          </div>
        </div>
      </div>
    </>
  )
}