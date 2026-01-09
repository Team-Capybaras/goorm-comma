import { Card } from '@/components/common/Card'

export default function ParkIn5km() {
  return (
    <div className="flex flex-col gap-md">
      <p className="text-subtitle-1-sb">지금{' '}
      <span className="text-primary">한적한 5km</span>{' '}
      이내 공원
      </p>
      <div className="flex gap-md overflow-x-auto">
        <Card className="w-[282] h-[362] border-default rounded-xl shrink-0">공원 이미지</Card>
        <Card className="w-[282] border-default rounded-xl shrink-0"></Card>
      </div>
    </div>
  )
}
