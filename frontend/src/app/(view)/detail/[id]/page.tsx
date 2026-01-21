import AlternativeParkDashboard from "@/app/(view)/detail/_components/AlternativeParkDashboard";
import Back from "@/components/common/Back";
import ParkDataClient from "@/app/(view)/detail/_components/ParkDataClient";

export default async function Page ({params}: {params: Promise<{id : string}>}) {
  const {id} = await params

  return (
    <div className="relative">
      <ParkDataClient areaCode={id} />
      {/* bar */}
      <div className="bg-gray-100 w-full h-[8px]"></div>
      {/* 대체 공원 */}
      <AlternativeParkDashboard areaCode={id}/>
      <Back />
    </div>
  )
}