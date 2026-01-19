import ParkThumbnail from "@/components/common/ParkThumbnail";
import Tag from "@/components/common/Tag";
import {ParkInfo} from "@/shared/types/park-types";

interface ParkCardProps {
  data : ParkInfo
}

export default function ParkCard ({data}: ParkCardProps) {
  if (!data) {
    return null
  }

  return (
    <div className="flex flex-col gap-8">
      <div key={data.areaName}>
        <div className="w-full h-[325px] border-default rounded-8 bg-gray-500 overflow-hidden">
          <ParkThumbnail
            height={325}
            dotBottom={12}
            data={data.images}
          />
        </div>

        <div className="mt-3">
          <p>
            <span className="text-body-1-sb mr-3">{data.areaName}</span>
            <span className="text-body-2-m">{data.distance}km</span>
          </p>
          <p>
            <span className="text-body-2-b mr-3">
              {data.areaCongestLevel}
            </span>
            <span className="text-caption-1-m">
              {data.temp}℃
            </span>
          </p>
        </div>

        <div className="flex gap-2 mt-2">
          {data.tags.map(tag => (
            <Tag key={tag}>{tag}</Tag>
          ))}
        </div>
      </div>
    </div>
  )
}