import {ParkInfo, ParkType} from "@/shared/types/park-types";
import Tag from "@/components/common/Tag";
import {CONGESTION_COLOR_MAP} from "@/shared/utils/congestion-helper";
import ParkInfoClient from "@/app/(view)/detail/_components/ParkInfoClient";

interface ParkInfoProps {
  data : ParkInfo
}

const PARK_IMAGE_MAP: Record<ParkType, string> = {
  COURSE: '/images/icons/route.svg',
  FACILITY: '/images/icons/flower.svg',
  NEARBY: '/images/icons/marker-small.svg',
}

export default async function ParkInfoDashboard({data}: ParkInfoProps) {
  console.log(data)

  return (
    <div className="px s-5">
      {/* 공원 이름 및 거리 */}
      <div className="flex items-center mb s-3">
        <p className="text-title-2-sb mr s-2-sub">{data?.areaName}</p>
        <p className="text-sub">{data?.distance ?? 0}Km</p>
      </div>

      {/* 혼잡도 요약, 주소, 길찾기 */}
      <div className="flex items-center">
        <p
          className={`font-b font-sm ${CONGESTION_COLOR_MAP[data?.areaCongestLevel]}`}
        >
          {data?.areaCongestLevel}
        </p>
        <div className="w-0.75 h-0.75 rounded-full mx-2 bg-deep"></div>
        <p className="text-sub-deep text-caption-1-m mr s-3">{data?.address}</p>
        <ParkInfoClient data={data}/>
      </div>

      {/* 공원 둘러보기 */}
      <div className="border-1-line-default rounded-2xl p s-4 mt-4">
        <h3 className="text-sub-deep text-body-2-sb">공원 둘러보기</h3>
        <div className="flex flex-col gap-1.5 mt s-3">
          {data.features.map((feature) => (
            <div key={feature.type} className="flex items-center justify-start gap-3">
              <img src={PARK_IMAGE_MAP[feature.type]} className="w-3.5 h-3.5" alt="아이콘"/>
              <p className="text-caption-1-m">{feature.description}</p>
            </div>
          ))}
        </div>
      </div>

      {/* 태그 */}
      {data?.tags?.length > 0 && (
        <div className="flex gap-2 mt s-4">
          <Tag variant="blue">{data.recommendedVisitHour}</Tag>
          {data.tags.map((tag) => (
            <Tag key={tag}>{tag}</Tag>
          ))}
        </div>
      )}
    </div>
  )
}