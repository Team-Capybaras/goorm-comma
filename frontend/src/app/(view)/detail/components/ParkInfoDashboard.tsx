interface ParkInfoDashboardProps {
  data?: {
    area_name: string;
    thumbnail: string[];
    temp: string;
    address: string;
    distance: string;
    air_ms: string;
    congestion: string;
    tags: string[]
  }
}

export default function ParkInfoDashboard({data}: ParkInfoDashboardProps) {

  return (
    <>
      {data?.area_name}
    </>
  )
}