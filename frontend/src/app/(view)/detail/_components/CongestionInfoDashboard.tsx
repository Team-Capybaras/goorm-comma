import {api} from "@/shared/libs/axios";
import CongestionChart from "@/app/(view)/detail/_components/CongestionChart";
import {CongestionType} from "@/shared/types/chart-types";
import ErrorComponent from "@/components/ui/ErrorComponent";
import AlternativeParkClient from "@/app/(view)/detail/_components/AlternativeParkClient";

interface CongestionInfoDashboardProps {
  areaCode: string;
}

export default async function CongestionInfoDashboard({areaCode} : CongestionInfoDashboardProps) {
  let data: CongestionType | null = null
  let error: boolean = false

  try {
    const res = await api.get(`/v1/avoidance/statistics`, {
      params: {
        area_code : areaCode
      }
    })

    data = res.data.data
  } catch (err) {
    console.error(err)
    error = true
  }

  if (error || !data) {
    return (
      <>
        <h3 className="text-body-1-sb">혼잡도</h3>
        <ErrorComponent className="mt s-4"></ErrorComponent>
      </>
    )
  }

  return (
    <div className="mt s-6">
      <CongestionChart data={data}/>
    </div>
  )
}