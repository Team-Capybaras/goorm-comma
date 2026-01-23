import SearchPark from './SearchPark'
import ParkIn5km from './ParkIn5km'
import ParkListWithPopulation from './ParkListWithPopulation'
import MyLocationButton from './MyLocationButton'

export default function ListView() {
  return (
    <>
      <div className="flex justify-center">
        <div className="w-full max-w-2xl bg-bright pb-3 flex flex-col gap-4">
          <div className="flex justify-between items-center px-5 pt-5">
            <MyLocationButton />
            <span className="text-body-2-m">현재 날씨</span>
          </div>

          <div className="px-5">
            <SearchPark />
          </div>
          <ParkIn5km />
        </div>
      </div>

      <ParkListWithPopulation />
    </>
  )
}