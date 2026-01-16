import { FacilityItem } from '@/shared/types/map-types'
import Image from 'next/image'
import {api} from "@/shared/libs/axios";
import TransportMap from "@/app/(view)/detail/_components/TransportMap";


interface TransportDashboardProps {
  areaCode: string;
}

export default async function TransportDashboard({ areaCode }: TransportDashboardProps) {
  const fetchData = async () => {
    const res = await api.get(`/v1/parking`, {
      params: {
        area_code: areaCode
      }
    })
    
    return (res.data.data)
  }
  const data:FacilityItem[] = await fetchData()

  if (!data) return null

  return (
    <div className="mt s-6 mb-6">
      <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>
      <TransportMap data={data} />
      <div className="grid grid-cols-3 mt-3">
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/parking.svg" width={16} height={16} alt={'주차장'} />
          <p className="font-xs">주차공간</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image
            src="/images/icons/facility/electric.svg"
            width={16}
            height={16}
            alt={'전기차 충전소'}
          />
          <p className="font-xs">전기차 충전소</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/bicycle.svg" width={16} height={16} alt={'따릉이'} />
          <p className="font-xs">따릉이</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/subway.svg" width={16} height={16} alt={'지하철'} />
          <p className="font-xs">지하철</p>
        </div>
        <div className="flex items-center justify-start gap s-2">
          <Image src="/images/icons/facility/bus.svg" width={16} height={16} alt={'버스'} />
          <p className="font-xs">버스</p>
        </div>
      </div>
    </div>
  )
}
