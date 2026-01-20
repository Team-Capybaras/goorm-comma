import { MouseEvent, useEffect } from 'react'
import { Card, CardHeader, CardContent, CardFooter } from '@/components/common/Card'
import Tag from '@/components/common/Tag'
import cn from '@/shared/utils/cn'
import { Bold } from 'lucide-react'

interface CongestionInfoCardProps {
  onClose?: (event: MouseEvent<HTMLButtonElement>) => void
}

export function CongestionInfoCard({ onClose }: CongestionInfoCardProps) {
  useEffect(() => {
    document.body.style.overflow = 'hidden'

    return () => {
      document.body.style.overflow = 'unset'
    }
  }, [])

  return (
    <Card className="w-full rounded-t-[20px] rounded-b-none bg-white shadow-xl border-none overflow-hidden animate-slide-up">
      {/* 1. 헤더 */}
      <CardHeader closable onClose={onClose} className="pb-2 pt-7 px-6">
        <h2 className="text-subtitle-2-sb text-gray-900">혼잡도란?</h2>
      </CardHeader>

      {/* 2. 본문 */}
      <CardContent className="flex flex-col gap-8 px-6 pb-2">
        <div className="text-body-2-r text-gray-800 leading-[1.5]">
          <p>사람이 얼마나 모여 있는지 나타내는 지표예요.</p>
          <p>
            <span className="font-sb">과거 평균 실시간 인구</span>,{' '}
            <span className="font-sb">면적 대비 인구 수</span>를 반영해요.
          </p>{' '}
        </div>

        <div className="w-full">
          <div className="flex justify-between text-caption-2-r text-gray-500 mb-2">
            <span>걷기 편해요</span>
            <span>걷기 불편할 수 있어요</span>
          </div>

          <div
            className="h-[6px] w-full rounded-full mb-3"
            style={{
              background: `linear-gradient(90deg, 
                rgb(var(--positive)) 0%, 
                rgb(var(--normal)) 35%, 
                rgb(var(--caution)) 65%, 
                rgb(var(--warning)) 100%)`,
            }}
          />

          <div className="flex justify-between items-center w-full">
            <CongestionTagColorWrapper status="positive">여유</CongestionTagColorWrapper>
            <CongestionTagColorWrapper status="normal">보통</CongestionTagColorWrapper>
            <CongestionTagColorWrapper status="caution">약간붐빔</CongestionTagColorWrapper>
            <CongestionTagColorWrapper status="warning">붐빔</CongestionTagColorWrapper>
          </div>
        </div>
      </CardContent>

      {/* 3. 푸터 */}
      <CardFooter className="pt-4 pb-10 px-6">
        <p className="text-caption-3-r text-gray-400 -tracking-[0.5px]">
          * 통신사 인구 데이터를 가공해 제공해요. 실제 현장과 다를 수 있어요.
        </p>
      </CardFooter>
    </Card>
  )
}
function CongestionTagColorWrapper({
  status,
  children,
}: {
  status: 'positive' | 'normal' | 'caution' | 'warning'
  children: React.ReactNode
}) {
  const styleMap = {
    positive: 'bg-positive text-positive',
    normal: 'bg-normal text-normal',
    caution: 'bg-caution text-caution',
    warning: 'bg-warning text-warning',
  }

  return (
    <Tag className={cn('rounded-full min-w-[52px] justify-center py-[5px]', styleMap[status])}>
      {children}
    </Tag>
  )
}
