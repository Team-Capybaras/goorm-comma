import Image from "next/image";
import {AIR_INDEX_COLOR_MAP, getAirIndexGrade} from "@/shared/utils/air-helpers";
import {api} from "@/shared/libs/axios";
import {formatDateTimePad} from "@/shared/utils/time-format";

interface Props {
  areaCode: string;
}

interface EnvironmentTypes {
  precptType: string;
  rainChance: number;
  temp: number;
  weatherTime: string;
  airIndexLevel: number;
  airIndex: string;
  humidity: number;
}

export default async function EnvironmentDashboard({areaCode}: Props) {
  const fetchData = async () => {
    const res = await api.get(`/v1/weather/current`, {
      params: {
        area_code : areaCode
      }
    })
    return res.data.data
  }

  const data:EnvironmentTypes = await fetchData()

  if (!data) return null

  const grade = getAirIndexGrade(data?.airIndexLevel)

  return (
    <div className="mt s-6">
      {/* sub title */}
      <div className="flex justify-between items-center">
        <h3 className="text-body-1-sb">날씨</h3>
        <p className="text-caption-3-m text-gray-300">{formatDateTimePad(data.weatherTime)} 기준</p>
      </div>
      {/* weather card */}
      <div className="border-1-line-default rounded-7 p s-4 mt s-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <Image src={"/images/icons/weather/sun.svg"} className="mr s-3" width={24} height={24} alt="해"/>
            <p className="text-body-2-m mr s-2">{data?.precptType}</p>
            <div className="flex items-center">
              <p className="text-body-2-m mr s-1">{data?.temp}</p>
              <p className="text-caption-3-m">℃</p>
            </div>
          </div>
          <div className="w-[1px] h-[16px] bg-gray-100"></div>
          <div className="flex items-center">
            <div className="flex items-center mr s-4">
              <p className="text-caption-1-m text-gray-500 mr s-2-sub">강수 확률</p>
              <p className="text-caption-1-m">{data?.rainChance}%</p>
            </div>
            <div className="flex items-center">
              <p className="text-caption-1-m text-gray-500 mr s-2-sub">습도</p>
              <p className="text-caption-1-m">{data?.humidity}%</p>
            </div>
          </div>
        </div>
        <div className="w-full h-[1px] my-4 bg-gray-100"></div>
        <div className="flex justify-between">
          <div className="flex items-center">
            <img src={"/images/icons/weather/air.svg"} width={24} height={24} alt="대기환경지수" />
            <p className="text-caption-1-m ml s-3">대기환경지수</p>
          </div>
          <p className={`text-caption-1-sb ${
            grade ? AIR_INDEX_COLOR_MAP[grade] : ''
          }`}>
            {data?.airIndexLevel} {data?.airIndex}
          </p>
        </div>
      </div>
    </div>
  )
}