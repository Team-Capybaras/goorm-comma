import {api} from "@/shared/libs/axios";
import CongestionChart from "@/app/(view)/detail/_components/CongestionChart";
import {CongestionType} from "@/shared/types/chart-types";

interface CongestionInfoDashboardProps {
  areaCode: string;
}

export default async function CongestionInfoDashboard({areaCode} : CongestionInfoDashboardProps) {

  const fetchData = async () => {
    const res = await api.get(`/v1/avoidance/statistics`, {
      params: {
        area_code : areaCode
      }
    })
    return res.data.data
  }

  const data:CongestionType = await fetchData()

  if (!data) return null

  return (
    <div className="mt s-6">
      <CongestionChart data={data}/>
    </div>
  )
}