
import ParkThumbnail from "@/components/common/ParkThumbnail";
import ParkInfoDashboard from "@/app/(view)/detail/_components/ParkInfoDashboard";
import EnvironmentDashboard from "@/app/(view)/detail/_components/EnvironmentDashboard";
import CongestionInfoDashboard from "@/app/(view)/detail/_components/CongestionInfoDashboard";
import TransportDashboard from "@/app/(view)/detail/_components/TransportDashboard";
import AlternativeParkDashboard from "@/app/(view)/detail/_components/AlternativeParkDashboard";

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

export default async function Page ({params}: {params: Promise<{id : string}>}) {
  ////////////////////////////
  /* TODO: 추후 API 작성 필요 */
  ///////////////////////////

  /*const res = await api.get(
    `${process.env.NEXT_PUBLIC_OPEN_API_URL}${process.env.NEXT_PUBLIC_OPEN_API_KEY}/json/citydata/1/5/POI104`
  );*/

  /*const cityData = res.data?.CITYDATA;*/
  const {id} = await params

  return (
    <div className="relative">
      <ParkThumbnail data={data.park.thumbnail}/>
      <div className="relative before:content-[''] before:w-full before:h-[32px] before:absolute before:top-[-32px] before:bg-white before:rounded-t-xl">
        {/* 공원 종합 정보 */}
        <ParkInfoDashboard areaCode={id}/>

        {/* bar */}
        <div className="bg-gray-100 w-full h-[1px] mt s-6"></div>

        {/* 날씨/혼잡도/대중교통 및 편의시설 */}
        <div className="px s-5 flex flex-col gap-xs rounded-xl">
          <EnvironmentDashboard areaCode={id}/>
          <CongestionInfoDashboard areaCode={id}/>
          <TransportDashboard areaCode={id}/>
        </div>
      </div>

      {/* bar */}
      <div className="bg-gray-100 w-full h-[8px]"></div>

      {/* 대체 공원 */}
      <AlternativeParkDashboard data={data.alternative}/>
    </div>
  )
}