import ParkThumbnail from "@/components/common/ParkThumbnail";
import Tag from "@/components/common/Tag";
import {ParkInfo} from "@/shared/types/park-types";
import {Card} from "@/components/common/Card";
import {CONGESTION_COLOR_MAP} from "@/shared/utils/congestion-helper";
import Link from "next/link";

interface ParkCardProps {
  data : ParkInfo
}

export default function ParkCard ({data}: ParkCardProps) {
  if (!data) {
    return null
  }

  return (
    <Card className="border-0">
      <Link href={`/detail/${data.areaCode}`}>
        <div className="w-full h-[325px] border-default rounded-8 bg-gray-500 overflow-hidden">
          <ParkThumbnail
            height={325}
            dotBottom={16}
            data={data.images}
          />
        </div>

        <div className="flex items-end mt s-4">
          <p className="text-body-1-sb mr s-2">{data.areaName}</p>
          <p className="text-body-2-m text-sub">{data.distance}km</p>
        </div>
        <div className="flex items-center mt s-3">
          <p className={`text-body-2-b ${CONGESTION_COLOR_MAP[data?.areaCongestLevel]}`}>
            {data.areaCongestLevel}
          </p>
          <div className="w-0.75 h-0.75 rounded-full mx s-2 bg-deep"></div>
          <img src="/images/icons/weather/umbrella-gray.svg" width={16} height={16} alt="우산"/>
          <p className="text-caption-1-m text-sub-deep">
            {data.precptMsg}
          </p>
        </div>

        <div className="flex flex-wrap gap-2 mt s-3">
          <Tag variant="blue">{data.recommendedVisitHour}</Tag>
          {data.tags.map(tag => (
            <Tag key={tag}>{tag}</Tag>
          ))}
        </div>
      </Link>
    </Card>
  )
}