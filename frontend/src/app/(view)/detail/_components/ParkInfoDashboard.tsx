import Image from 'next/image'
import {ParkInfo} from "@/shared/types/park-types";
import Tag from "@/components/common/Tag";
import {CONGESTION_COLOR_MAP} from "@/shared/utils/congestion-helper";

interface ParkInfoProps {
  data : ParkInfo
}

export default async function ParkInfoDashboard({data}: ParkInfoProps) {

  return (
    <div className="px s-5">
      <div className="flex items-center mb s-3">
        <p className="text-title-2-sb mr s-2-sub">{data?.areaName}</p>
        <p className="text-sub">{data?.distance ?? 0}Km</p>
      </div>
      <div className="flex items-center">
        <p
          className={`font-b font-sm ${CONGESTION_COLOR_MAP[data?.areaCongestLevel]}`}
        >
          {data?.areaCongestLevel}
        </p>
        <div className="w-[3px] h-[3px] rounded-full mx s-2 bg-deep"></div>
        <p className="text-sub-deep text-caption-1-m mr s-3">{data?.address}</p>
        <a href={`https://map.kakao.com/link/to/${data?.areaName},${data?.latitude},${data.longitude}`} className="flex items-center" target={"_blank"}>
          <p className="text-caption-1-m text-sub-bright">길찾기</p>
          <Image src={"/images/icons/arrow/right-gray.svg"} width={16} height={16} alt={"바로가기"}/>
        </a>
      </div>
      {data?.tags?.length > 0 && (
        <div className="flex gap-2 mt s-3">
          {data.tags.map((tag) => (
            <Tag key={tag}>{tag}</Tag>
          ))}
        </div>
      )}
    </div>
  )
}