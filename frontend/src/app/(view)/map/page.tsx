import FloatingBar from '@/components/common/FloatingBar'
import MainMap from '@/components/map/MainMap'
import type { ParkItem } from '@/shared/types/map-types'

const PARK_DATA: ParkItem[] = [
  {
    id: 'poi-1',
    category: 'PARK',
    name: '여의도 한강공원',
    lat: 37.5284,
    lng: 126.9331,
    areaCode: 'POI001',
    congestion: '약간 붐빔',
    distance: '5km',
    weather: {
      iconStatus: '비',
      text: '비 소식 있어요',
    },
    airQuality: '대기질이 나빠요',
    image: '',
    forecast: '14시 여유 예상',
    tags: ['주차장', '강류', '지하철역'],
  },
  {
    id: 'poi-2',
    category: 'PARK',
    name: '서울숲',
    lat: 37.5444,
    lng: 127.0374,
    areaCode: 'POI002',
    congestion: '여유',
    distance: '2.1km',
    weather: {
      iconStatus: '맑음',
      text: '24℃ 맑음',
    },
    forecast: undefined,
    tags: ['산책', '피크닉', '사슴'],
  },
  {
    id: 'poi-3',
    category: 'PARK',
    name: '반포 한강공원',
    lat: 37.5103,
    lng: 126.996,
    areaCode: 'POI003',
    congestion: '붐빔',
    distance: '8.4km',
    weather: {
      iconStatus: '구름',
      text: '흐림',
    },
    airQuality: '보통',
    image: '',
    tags: ['무지개분수', '야시장'],
  },
]

export default function MapPage() {
  const initialCenter = { lat: 37.5284, lng: 126.9331 }
  return (
    <div className="w-full h-[100dvh] relative bg-default overflow-hidden">
      <MainMap data={PARK_DATA} center={initialCenter} />
    </div>
  )
}
