import Image from "next/image";

interface ParkInfoDashboardProps {
  data?: {
    area_name: string;
    thumbnail: string[];
    temp: string;
    address: string;
    distance: string;
    air_ms: string;
    congestion: string;
    tags: string[];
  }
}

export default function ParkInfoDashboard({data}: ParkInfoDashboardProps) {

  return (
    <div className="px s-5">
      <div className="flex items-center mb s-3">
        <p className="text-title-2-sb mr s-2-sub">{data?.area_name}</p>
        <p className="text-sub">{data?.distance}Km</p>
      </div>
      <div className="flex items-center">
        <p className="text-green-500 font-b font-sm">{data?.congestion}</p>
        <div className="w-[3px] h-[3px] rounded-full mx s-2 bg-deep"></div>
        <p className="text-sub-deep text-caption-1-m mr s-3">{data?.address}</p>
        <a href={`https://map.kakao.com/link/to/${data?.area_name},37.3952969470752,127.110449292622`} className="flex items-center" target={"_blank"}>
          <p className="text-caption-1-m text-sub-bright">길찾기</p>
          <Image src={"/images/icons/arrow/right-gray.svg"}  width={16} height={16} alt={"바로가기"}/>
        </a>
      </div>
      <div className="flex gap-2 mt s-3">
        {data?.tags.map((tag,i) => (
          <span className="rounded-sm bg-default px s-3 py-[1px] text-caption-1-sb text-sub-deep" key={i}>{tag}</span>
        ))}
      </div>
    </div>
  )
}