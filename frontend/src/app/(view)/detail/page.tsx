import {api} from "@/shared/libs/axios";
import EnvironmentDashboard from "@/app/(view)/detail/components/EnvironmentDashboard";
import FacilityDashboard from "@/app/(view)/detail/components/FacilityDashboard";
import TransportDashboard from "@/app/(view)/detail/components/TransportDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/components/CongestionInfoDashboard";
import AlternativeParkDashboard from "@/app/(view)/detail/components/AlternativeParkDashboard";
import ParkInfoDashboard from "@/app/(view)/detail/components/ParkInfoDashboard";

export default async function page () {
  const res = await api.get(
    `${process.env.NEXT_PUBLIC_OPEN_API_URL}${process.env.NEXT_PUBLIC_OPEN_API_KEY}/json/citydata/1/5/POI104`
  );

  const cityData = res.data?.CITYDATA;

  return (
    <>
      <ParkInfoDashboard data={cityData.AREA_NM}/>
      <EnvironmentDashboard data={cityData.WEATHER_STTS}/>
      <FacilityDashboard/>
      <TransportDashboard data={cityData.ROAD_TRAFFIC_STTS}/>
      <CongestionInfoDashboard />
      <AlternativeParkDashboard />
    </>
  )
}