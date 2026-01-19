import {Card} from "@/components/common/Card";
import ParkThumbnail from "@/components/common/ParkThumbnail";
import Tag from "@/components/common/Tag";
import {ParkListWithPage} from "@/shared/types/park-types";

interface ParkCardProps {
  data : ParkListWithPage
}

export default function ParkCard ({data}: ParkCardProps) {
  if (!data) {
    return null
  }

  return (
    <div>
      <p className="pb-5">
        <span className="text-body-2-sb">총 {data?.parks.length}개</span>
        <span className="text-body-2-m text-sub-bright">의 공원</span>
      </p>
      <div className="flex flex-col gap-8">
        {data.parks.map((park) => (
          <div key={park.areaName}>
            <div className="w-full h-[325px] border-default rounded-8 bg-gray-500 overflow-hidden">
              <ParkThumbnail
                height={325}
                dotBottom={12}
                data={park.images}
              />
            </div>

            <div className="mt-3">
              <p>
                <span className="text-body-1-sb mr-3">{park.areaName}</span>
                <span className="text-body-2-m">{park.distance}km</span>
              </p>
              <p>
                <span className="text-body-2-b mr-3">
                  {park.areaCongestLevel}
                </span>
                <span className="text-caption-1-m">
                  {park.temp}℃
                </span>
              </p>
            </div>

            <div className="flex gap-2 mt-2">
              {park.tags.map(tag => (
                <Tag key={tag}>{tag}</Tag>
              ))}
            </div>
          </div>
        ))}
    </div>
  </div>
  )
}