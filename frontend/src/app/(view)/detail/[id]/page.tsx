
import ParkThumbnail from "@/components/common/ParkThumbnail";
import ParkInfoDashboard from "@/app/(view)/detail/_components/ParkInfoDashboard";
import EnvironmentDashboard from "@/app/(view)/detail/_components/EnvironmentDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/_components/CongestionInfoDashboard";
import TransportDashboard from "@/app/(view)/detail/_components/TransportDashboard";
import AlternativeParkDashboard from "@/app/(view)/detail/_components/AlternativeParkDashboard";
import {api} from "@/shared/libs/axios";
import {ParkInfo} from "@/shared/types/park-types";

export default async function Page ({params}: {params: Promise<{id : string}>}) {
  const {id} = await params

  const fetchData = async () => {
    const res = await api.get(`/v1/parks/${id}`)
    console.log(res)
    return res.data.data.park
  }
  const data:ParkInfo = await fetchData()

  return (
    <div className="relative">
      <ParkThumbnail data={data.images}/>
      <div className="relative before:content-[''] before:w-full before:h-[32px] before:absolute before:top-[-32px] before:bg-white before:rounded-t-xl">
        {/* 공원 종합 정보 */}
        <ParkInfoDashboard data={data}/>

        {/* bar */}
        <div className="bg-gray-100 w-full h-[1px] mt s-6"></div>

        {/* 날씨/혼잡도/대중교통 및 편의시설 */}
        <div className="px s-5 flex flex-col gap-xs rounded-xl">
          <EnvironmentDashboard areaCode={id}/>
          <CongestionInfoDashboard areaCode={id}/>
          <TransportDashboard areaCode={id} center={{lat: data.latitude, lng: data.longitude}}/>
        </div>
      </div>

      {/* bar */}
      <div className="bg-gray-100 w-full h-[8px]"></div>

      {/* 대체 공원 */}
      <AlternativeParkDashboard areaCode={id}/>
    </div>
  )
}