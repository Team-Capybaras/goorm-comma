import { Card } from '@/components/common/Card'
import ParkThumbnail from '@/components/common/ParkThumbnail'

export default function ParkIn5km() {
  return (
    <div className="flex flex-col gap-3">
      <p className="text-subtitle-1-sb">지금{' '}
      <span className="text-primary">한적한 5km</span>{' '}
      이내 공원
      </p>
      <Card className="w-[345] border-0 rounded-[0]">
        <ParkThumbnail
          height={362}     // ✅ 이 페이지 전용
          showDots={false}
          layout="peek"
          data={[
            '/test.jpg',
            '/test.jpg',
            '/test.jpg',
            '/test.jpg',
          ]} 
        />
      </Card>
    </div>
  )
}
