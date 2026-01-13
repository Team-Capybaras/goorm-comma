import {api} from "@/shared/libs/axios";
import ParkThumbnail from "@/app/(view)/detail/components/ParkThumbnail";
import ParkInfoDashboard from "@/app/(view)/detail/components/ParkInfoDashboard";
import EnvironmentDashboard from "@/app/(view)/detail/components/EnvironmentDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/components/CongestionInfoDashboard";
import TransportDashboard from "@/app/(view)/detail/components/TransportDashboard";
import AlternativeParkDashboard from "@/app/(view)/detail/components/AlternativeParkDashboard";
import {MapDataType} from "@/shared/types/map-types";

const data = {
  park : {
    thumbnail : [
      "/test.jpg",
      "/test.jpg",
      "/test.jpg",
      "/test.jpg",
      "/test.jpg",
    ],
    area_name : "서울숲",
    congestion : "여유",
    distance: "2.1",
    temp : "3.6",
    air_ms: "대기질 좋음",
    address: "서울 성동구 성수동 1가",
    tags : [
      "태그1",
      "태그2",
      "태그3",
    ],
  },
  weather : {
    today: "맑음",
    temp : "3.6",
    air : "51 ~ 100",
    air_ms : "좋음",
    precipitation : "25",
    humidity : "40",
    weather_time : "2025.12.30 18:30",
  },
  congestion: {
    refresh_time: "2025.12.30 18:30",
    predict: "17",
    transition: {
      monday: {
        past: ['1', '2', '12', '15', '10', '10', '15', '8', '10'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      tuesday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      wednesday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      thursday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      friday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      saturday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
      sunday: {
        past: ['10', '20', '50', '60', '30', '20', '50', '80', '20'],
        now: ['15', '25', '45', '55', '35', '25', '45', '70', '30'],
        future: ['30', '40', '60', '70', '50', '40', '60', '85', '45'],
      },
    }
  },
  alternative : [
    {
      thumbnail : "/test.jpg",
      area_name: "어린이 대공원",
      congestion: "한적함",
      distance: "1.2"
    },
    {
      thumbnail : "/test.jpg",
      area_name: "공원",
      congestion: "혼잡",
      distance: "1.7"
    },
    {
      thumbnail : "/test.jpg",
      area_name: "공원인가",
      congestion: "보통",
      distance: "5.2"
    },
    {
      thumbnail : "/test.jpg",
      area_name: "공공원원",
      congestion: "보통",
      distance: "7.2"
    },
    {
      thumbnail : "/test.jpg",
      area_name: "공공원원",
      congestion: "보통",
      distance: "7.2"
    },
    {
      thumbnail : "/test.jpg",
      area_name: "공공원원",
      congestion: "보통",
      distance: "7.2"
    },
  ]
}

export default async function page () {

  ////////////////////////////
  /* TODO: 추후 API 작성 필요 */
  ///////////////////////////

  /*const res = await api.get(
    `${process.env.NEXT_PUBLIC_OPEN_API_URL}${process.env.NEXT_PUBLIC_OPEN_API_KEY}/json/citydata/1/5/POI104`
  );*/

  /*const cityData = res.data?.CITYDATA;*/

  return (
    <div className="relative">
      <ParkThumbnail data={data.park.thumbnail}/>
      <div className="relative before:content-[''] before:w-full before:h-[32px] before:absolute before:top-[-32px] before:bg-white before:rounded-t-xl">
        {/* 공원 종합 정보 */}
        <ParkInfoDashboard data={data.park}/>

        {/* bar */}
        <div className="bg-gray-100 w-full h-[1px] mt s-6"></div>

        {/* 날씨/혼잡도/대중교통 및 편의시설 */}
        <div className="px s-5 flex flex-col gap-xs rounded-xl">
          <EnvironmentDashboard data={data.weather}/>
          <CongestionInfoDashboard data={data.congestion}/>
          <TransportDashboard/>
        </div>
      </div>

      {/* bar */}
      <div className="bg-gray-100 w-full h-[8px]"></div>

      {/* 대체 공원 */}
      <AlternativeParkDashboard data={data.alternative}/>
    </div>
  )
}