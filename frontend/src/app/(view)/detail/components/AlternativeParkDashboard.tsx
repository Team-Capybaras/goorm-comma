'use client'

import Image from "next/image";
import useEmblaCarousel from "embla-carousel-react";

interface AlternativeParkDashboardProps {
  data?: [
    {
      thumbnail : string,
      area_name: string,
      congestion: string,
      distance: string
    }
  ]
}

export default function AlternativeParkDashboard({data}: AlternativeParkDashboardProps) {
  const [emblaRef] = useEmblaCarousel()

  return (
    <div className="mt-3">
      <h3 className="pl-4 font-sb">지금 갈만한 공원</h3>
      <div className="embla mt-3">
        {/* viewport */}
        <div className="overflow-hidden relative" ref={emblaRef}>
          {/*container*/}
          <div className="flex gap-x-3">
            {data?.map((item, i) => (
              /* slide */
              <div className={`flex-[0_0_70%] ${i==0 ? 'pl-4' : ''}`} key={i}>
                <div className=" min-w-0">
                  <div className="relative w-full h-[218px]">
                    <Image
                      src={item.thumbnail}
                      alt={`park-thumbnail-${i}`}
                      fill
                      className="object-cover"
                      priority={i === 0}
                    />
                  </div>
                </div>
                <div className="flex items-center mt-3">
                  <p className="font-sb font-sm">{item?.area_name}</p>
                  <div className="w-[3px] h-[3px] rounded-full bg-gray-300 mx-2"></div>
                  <p className="font-sm text-gray-500">{item?.distance}</p>
                </div>
                <div>
                  <p className="font-b font-sm text-primary">{item?.congestion}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}