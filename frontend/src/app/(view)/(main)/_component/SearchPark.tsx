import {api} from "@/shared/libs/axios";
import SearchClient from "@/app/(view)/(main)/_component/SearchClient";
import {ParkList} from "@/shared/types/park-types";

export default async function SearchPark() {
  const fetchData = async () => {
    const res = await api.get('/v1/parks/all')

    return res.data.data.parks
  }

  const data:ParkList[] = await fetchData()

  return (
    <>
      <div className="flex flex-col gap-3 relative">
        <p className="text-title-1-b">
          쉬기 좋은 공원을 찾아볼까요?
        </p>
        <SearchClient data={data}/>
      </div>
    </>
  )
}
