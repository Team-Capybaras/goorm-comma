import ListView from './_component/ListView'
import FloatingBar from '@/components/common/FloatingBar'
import { LocationProvider } from '@/providers/LocationProvider'

export default function page() {
  return (
    <LocationProvider>
      <ListView />
      <FloatingBar />
    </LocationProvider>
  )
}
