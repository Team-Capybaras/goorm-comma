interface ParkInfoDashboardProps {
  data?: {
    area_name: string;
    thumbnail: string[];
    temp: string;
    address: string;
    distance: string;
    air_ms: string;
    congestion: string;
    tags: string[]
  }
}

export default function ParkInfoDashboard({data}: ParkInfoDashboardProps) {

  return (
    <div>
      <div className="flex items-end gap-1 mb-2">
        <p className="text-title-2-sb mr-2">{data?.area_name}</p>
        <p className="text-gray-500">{data?.distance}Km</p>
      </div>
      <div className="flex items-center gap-1 mb-2">
        <p className="text-green-500 font-b font-sm">{data?.congestion}</p>
        <div className="w-[3px] h-[3px] rounded-full bg-gray-300 mx-2"></div>
        <p className="text-gray-600 text-caption-1-m mr-2">{data?.address}</p>
        <a href="/" className="text-caption-1-m text-gray-400">길찾기 </a>
      </div>
      <div className="flex gap-2">
        {data?.tags.map((tag,i) => (
          <span className="rounded-sm bg-default px-3 py-1 font-xs text-sub-deep" key={i}>{tag}</span>
        ))}
      </div>
    </div>
  )
}