'use client'

import SearchPark from './SearchPark'
import ParkIn5km from './ParkIn5km'
import ParkListWithPopulation from './ParkListWithPopulation'
import MyLocationButton from './MyLocationButton'
import { useCurrentLocation } from '@/hooks/useCurrentLocation'

export default function ListView() {
  const {
    location,
    locationName,
    loading,
    getCurrentLocation,
  } = useCurrentLocation()

  return (
    <div className="bg-default">
      <div className="flex bg-bright justify-center mb-3">
        <div className="w-full max-w-2xl flex flex-col gap-4 p-5">

          <div className="flex justify-between items-center">
            <MyLocationButton
              label={locationName ?? '현재 위치'}
              onClick={getCurrentLocation}
              loading={loading}
            />
            <span className="text-body-2-m">현재 날씨</span>
          </div>

          <SearchPark />
          <ParkIn5km />
        </div>
      </div>

      <ParkListWithPopulation />
    </div>
  )
}