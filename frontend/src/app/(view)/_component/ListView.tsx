import Image from 'next/image'
import ParkIn5km from './ParkIn5km'
import SearchPark from './SearchPark'
import ParkListWithPopulation from './ParkListWithPopulation'

export default function ListView() {
  return (
    <>
      <div className="flex bg-bright justify-center mb-3">
        <div className="w-full max-w-2xl flex flex-col gap-4 p-5">
        {/* 위치 및 날씨 정보 컨테이너 */}
          <div className="flex justify-between">
            <span className="inline-flex text-caption-1-m text-sub">
              <Image
                src="/images/icons/current.svg"
                alt="현재 위치"
                width={18}
                height={18}
                className="mb-1"
              />
              현재 위치
            </span>
            <span className="text-body-2-m">현재 날씨</span>
          </div>
          <SearchPark />
          <ParkIn5km />
        </div>
      </div>
      <ParkListWithPopulation />
    </>
  )
}
