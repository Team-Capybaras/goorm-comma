import {api} from "@/shared/libs/axios";
import ParkThumbnail from "@/components/common/ParkThumbnail";
import ParkInfoDashboard from "@/app/(view)/detail/_components/ParkInfoDashboard";
import EnvironmentDashboard from "@/app/(view)/detail/_components/EnvironmentDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/_components/CongestionInfoDashboard";
import FacilityDashboard from "@/app/(view)/detail/_components/FacilityDashboard";
import AlternativeParkDashboard from "@/app/(view)/detail/_components/AlternativeParkDashboard";
import ParkInfoDashboardError from "@/app/(view)/detail/_status/ParkInfoDashboardError";
import ErrorComponent from "@/components/ui/ErrorComponent";
import {ParkInfo} from "@/shared/types/park-types";
import FacilityInfo from "@/app/(view)/detail/_components/FacilityInfo";

export default async function Page ({params}: {params: Promise<{id : string}>}) {
  const {id} = await params

  let data: ParkInfo | null = null
  let error: boolean = false

  try {
    const res = await api.get(`/v1/parks/${id}`)
    data = res.data.data.park
  } catch (err) {
    console.error(err)
    error = true
  }

  return (
    <div className="relative">
      {(!error && data)? <ParkThumbnail data={data.images}/> : <ErrorComponent />}
      <div className="relative before:content-[''] before:w-full before:h-[32px] before:absolute before:top-[-32px] before:bg-white before:rounded-t-xl">
        {/* 공원 종합 정보 */}
        {(!error && data)? <ParkInfoDashboard data={data}/> : <ParkInfoDashboardError />}


        {/* bar */}
        <div className="bg-gray-100 w-full h-[1px] mt s-6"></div>

        {/* 날씨/혼잡도/대중교통 및 편의시설 */}
        <div className="px s-5 flex flex-col gap-xs  rounded-xl">
          <EnvironmentDashboard areaCode={id}/>
          <CongestionInfoDashboard areaCode={id}/>
          {(!error && data)?
            <FacilityDashboard areaCode={id} center={{lat: data.latitude, lng: data.longitude}}/> : (
              <div className="mb-5 mt s-6">
                <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
                <ErrorComponent className={"mt-2"}/>
                <FacilityInfo />
              </div>
            )
          }
        </div>
      </div>

      {/* bar */}
      <div className="bg-gray-100 w-full h-[8px]"></div>

      {/* 대체 공원 */}
      <AlternativeParkDashboard areaCode={id}/>
    </div>
  )
}