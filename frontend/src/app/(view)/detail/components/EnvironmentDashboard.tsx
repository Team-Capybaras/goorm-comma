export default function EnvironmentDashboard ({data}:any) {

  return (
    <>
      {data[0].PCP_MSG}
      {data[0].UV_MSG}
    </>
  )
}