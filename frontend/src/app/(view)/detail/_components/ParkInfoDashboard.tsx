import Image from 'next/image'
import {ParkInfo} from "@/shared/types/park-types";
import Tag from "@/components/common/Tag";
import {CONGESTION_COLOR_MAP} from "@/shared/utils/congestion-helper";
import ParkInfoClient from "@/app/(view)/detail/_components/ParkInfoClient";

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
        <ParkInfoClient data={data}/>
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