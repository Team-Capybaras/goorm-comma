import Image from "next/image";

interface EnvironmentDashboardProps {
  data?: {
    today: string;
    precipitation: string;
    temp: string;
    weather_time: string;
    air: string;
    air_ms: string;
    humidity: string
  }
}

export default function EnvironmentDashboard({data}: EnvironmentDashboardProps) {

  return (
    <div className="mt-8">
      <div className="flex justify-between items-end">
        <h3 className="font-sb">날씨</h3>
        <p className="font-3xs text-gray-400">{data?.weather_time}</p>
      </div>
      <div className="border-1-line-default rounded-xl p-4 mt-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <Image src={"/images/icons/weather/sun.svg"} className="mr-3" width={24} height={24} alt="해"/>
            <p className="font-sm mr-2">{data?.today}</p>
            <div className="flex itens-center"><p className="font-sm">{data?.temp}</p> <span className="font-3xs">℃</span></div>
          </div>
          <div className="w-[1px] h-[20px] bg-gray-100"></div>
          <div className="flex items-center">
            <div className="flex items-center mr-5">
              <p className="font-xs text-gray-500 mr-3">강수 확률</p>
              <p className="font-xs">{data?.precipitation}%</p>
            </div>
            <div className="flex items-center">
              <p className="font-xs text-gray-500 mr-3">습도</p>
              <p className="font-xs">{data?.humidity}</p>
            </div>
          </div>
        </div>
        <div className="w-full h-[1px] my-4 bg-gray-100"></div>
        <div className="flex justify-between">
          <div className="flex itens-center">
            <Image src={"/images/icons/weather/air.svg"} className="mr-3" width={24} height={24} alt="대기환경지수" />
            <p className="font-xs">대기환경지수</p>
          </div>
          <p className="font-xs text-green-400">{data?.air} {data?.air_ms}</p>
        </div>
      </div>
    </div>
  )
}