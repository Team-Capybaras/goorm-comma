'use client'

import DetailMap from "@/components/map/DetailMap";
import {useState} from "react";

const center = {
  lat: 37.5444,
  lng: 127.0374,
}

export default function TransportMap ({data}) {
  const [isFullMapOpen, setIsFullMapOpen] = useState(false)

  return (
    <>
      <div className="w-full h-60 mt-3 rounded-xl overflow-hidden">
        <DetailMap
          data={[]}
          center={center}
          mode="preview"
          onExpand={() => setIsFullMapOpen(true)}
        />
      </div>
      {isFullMapOpen && (
        <div className="fixed inset-0 z-50 w-full h-full bg-background animate-slide-up">
          <DetailMap
            data={[]}
            center={center}
            mode="full"
            onClose={() => setIsFullMapOpen(false)}
          />
        </div>
      )}
    </>
  )
}