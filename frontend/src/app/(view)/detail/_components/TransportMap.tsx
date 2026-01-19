'use client'

import DetailMap from "@/components/map/DetailMap";
import {useState} from "react";
import {FacilityItem} from "@/shared/types/map-types";

export interface MapCenter {
  lat: number
  lng: number
}

interface TransportMapProps {
  data: FacilityItem[]
  center: MapCenter
}

export default function TransportMap ({ data, center }: TransportMapProps) {
  const [isFullMapOpen, setIsFullMapOpen] = useState(false)

  return (
    <>
      <div className="w-full h-60 mt-3 rounded-xl overflow-hidden">
        <DetailMap
          data={data}
          center={center}
          mode="preview"
          onExpand={() => setIsFullMapOpen(true)}
        />
      </div>
      {isFullMapOpen && (
        <div className="fixed inset-0 z-50 w-full h-full bg-background animate-slide-up">
          <DetailMap
            data={data}
            center={center}
            mode="full"
            onClose={() => setIsFullMapOpen(false)}
          />
        </div>
      )}
    </>
  )
}