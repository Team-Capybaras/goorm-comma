'use client'

import {ParkInfo} from "@/shared/types/park-types";
import {api} from "@/shared/libs/axios";
import ParkThumbnail from "@/components/common/ParkThumbnail";
import ErrorComponent from "@/components/ui/ErrorComponent";
import ParkInfoDashboard from "@/app/(view)/detail/_components/ParkInfoDashboard";
import FacilityDashboard from "@/app/(view)/detail/_components/FacilityDashboard";
import FacilityInfo from "@/app/(view)/detail/_components/FacilityInfo";
import ParkInfoDashboardError from "@/app/(view)/detail/_status/ParkInfoDashboardError";
import {useLocationStore} from "@/store/location.store";
import {useEffect, useState} from "react";
import EnvironmentDashboard from "@/app/(view)/detail/_components/EnvironmentDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/_components/CongestionInfoDashboard";
import ParkThumbnailSkeleton from "@/app/(view)/detail/_status/ParkThumbnailSkeleton";
import ParkInfoDashboardSkeleton from "@/app/(view)/detail/_status/ParkInfoDashboardSkeleton";
import FacilityDashboardSkeleton from "@/app/(view)/detail/_status/FacilityDashboardSkeleton";

export default function ParkDataClient({areaCode}:{areaCode: string} ) {
  const { location } = useLocationStore()

  const [data, setData] = useState<ParkInfo>()
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(false)

  useEffect(() => {
    if (!location) return

    const fetchData = async () => {
      setLoading(true)
      setError(false)

      try {
        const res = await api.get(`/v1/parks/${areaCode}`, {
          params: {
            latitude: location.lat,
            longitude: location.lng,
          },
        })

        setData(res.data.data.park)
      } catch (e) {
        console.error(e)
        setError(true)
      } finally {
        setLoading(false)
      }
    }

    fetchData()
  }, [location, areaCode])

  return (
    <>
      {loading ? <ParkThumbnailSkeleton /> : (error || !data) ? <ErrorComponent /> : <ParkThumbnail data={data.images}/> }
      {/* 공원 종합 정보 */}
      <div className="relative before:content-[''] before:w-full before:h-[32px] before:absolute before:top-[-32px] before:bg-white before:rounded-t-xl">
        {loading ? <ParkInfoDashboardSkeleton />: (error || !data) ? <ParkInfoDashboardError />  : <ParkInfoDashboard data={data}/> }
        <div className="bg-gray-100 w-full h-[1px] mt s-6"></div>
        <div className="px s-5 flex flex-col gap-xs  rounded-xl">
          <EnvironmentDashboard areaCode={areaCode}/>
          <CongestionInfoDashboard areaCode={areaCode}/>
          {loading ? <FacilityDashboardSkeleton /> :(!error && data) ?
            <FacilityDashboard areaCode={areaCode} center={{lat: data.latitude, lng: data.longitude}}/> : (
              <div className="mb-5 mt s-6">
                <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
                <ErrorComponent className={"mt-2"}/>
                <FacilityInfo />
              </div>
            )
          }
        </div>
      </div>
    </>
  )
}