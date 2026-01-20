import Image from "next/image";
import AlternativeParkClient from "@/app/(view)/detail/_components/AlternativeParkClient";
import EmblaCarousel from "@/components/common/EmblaCarousel";
import {CONGESTION_COLOR_MAP} from "@/shared/utils/congestion-helper";
import {api} from "@/shared/libs/axios";
import {ParkInfo} from "@/shared/types/park-types";
import ErrorComponent from "@/components/ui/ErrorComponent";

interface AlternativeParkDashboardProps {
  areaCode: string;
}

export default async function AlternativeParkDashboard({areaCode}: AlternativeParkDashboardProps) {
  let data: ParkInfo[] = []
  let error: boolean = false

  try {
    const res =await api.get(`/v1/parks/low-congestion`, {
      params: {
        cursor: areaCode,
        size: 10,
        longitude: 127.069903,
        latitude: 37.529546
      }
    })

    data = Array.isArray(res.data.data.parks) ? res.data.data.parks : []
    console.log(res)
  } catch (err) {
    console.error(err)
    error = true
  }

  if (error || data.length === 0) {
    return (
      <div className="mt s-5 pb-8">
        <div className="flex gap s-1 relative">
          <h3 className="pl s-6 text-subtitle-2-sb">지금 갈만한 공원</h3>
          <AlternativeParkClient />
        </div>
        <ErrorComponent className={"bg-white"} />
      </div>
    )
  }

  return (
    <div className="mt s-5 pb-8">
      <div className="flex gap s-1 relative">
        <h3 className="pl s-6 text-subtitle-2-sb">지금 갈만한 공원</h3>
        <AlternativeParkClient />
      </div>
      <div className="mt s-4">
        <EmblaCarousel containerClassName={'gap-x-3'}>
          <>
            {data?.map((item, i) => (
              <div className={`flex-[0_0_40%] ${i == 0 ? 'ml s-6' : ''}`} key={i}>
                <div className="relative w-full h-[220px]">
                  <Image
                    src={item.images[0]}
                    alt={`park-thumbnail-${i}`}
                    fill
                    sizes="100%"
                    className="object-cover rounded-6"
                    priority={i === 0}
                  />
                </div>

                <div className="flex items-center mt s-3">
                  <p className="text-body-2-sb">{item.areaName}</p>
                  <div className="w-[3px] h-[3px] rounded-full bg-gray-300 mx s-2"/>
                  <p className="text-body-2-m text-gray-500">{item.distance}km</p>
                </div>

                <p className={`text-caption-1-sb ${CONGESTION_COLOR_MAP[item.areaCongestLevel]}`}>
                  {item.areaCongestLevel}
                </p>
              </div>
            ))}
          </>
        </EmblaCarousel>
      </div>
    </div>
  )
}