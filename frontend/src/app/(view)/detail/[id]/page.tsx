import Back from "@/components/common/Back";
import ParkDataClient from "@/app/(view)/detail/_components/ParkDataClient";

export default async function Page ({params}: {params: Promise<{id : string}>}) {
  const {id} = await params

  return (
    <div className="relative">
      <ParkDataClient areaCode={id} />
      <Back />
    </div>
  )
}