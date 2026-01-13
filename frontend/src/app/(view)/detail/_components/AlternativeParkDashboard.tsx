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
    <div className="mt s-5 pb-8">
      <div className="flex gap s-1">
        <h3 className="pl s-6 text-subtitle-2-sb">지금 갈만한 공원</h3>
        <Image src={"/images/icons/info.svg"} width={18} height={18} alt={"안내"}/>
      </div>
      <div className="embla mt s-4">
        {/* viewport */}
        <div className="overflow-hidden relative" ref={emblaRef}>
          {/*container*/}
          <div className="flex gap-x-3">
            {data?.map((item, i) => (
              /* slide */
              <div className={`flex-[0_0_40%] ${i==0 ? 'ml s-6' : ''}`} key={i}>
                <div className=" min-w-0">
                  <div className="relative w-full h-[220px]">
                    <Image
                      src={item.thumbnail}
                      alt={`park-thumbnail-${i}`}
                      fill
                      className="object-cover rounded-6"
                      priority={i === 0}
                    />
                  </div>
                </div>
                <div className="flex items-center mt s-3">
                  <p className="text-body-2-sb">{item?.area_name}</p>
                  <div className="w-[3px] h-[3px] rounded-full bg-gray-300 mx s-2"></div>
                  <p className="text-body-2-m text-gray-500">{item?.distance}</p>
                </div>
                <div>
                  <p className="text-caption-1-sb text-primary">{item?.congestion}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}