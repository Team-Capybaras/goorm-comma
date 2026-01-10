'use client'

import { ComponentProps } from 'react'
import { Card, CardContent } from '@/components/common/Card' // 기존 Card 컴포넌트 임포트
import { getCongestionColorValue } from '@/shared/utils/map-helpers'
import { CongestionLevel } from '@/shared/types/map-types'
import cn from '@/shared/utils/cn'

interface MapCardProps extends ComponentProps<typeof Card> {
  congestion: CongestionLevel
  children: React.ReactNode
}

export default function MapCard({ congestion, className, children, ...props }: MapCardProps) {
  // 혼잡도에 따른 색상 값 가져오기
  const borderColor = getCongestionColorValue(congestion)

  return (
    <Card
      className={cn('border-[1.5px] shadow-sm transition-colors', className)}
      style={{ borderColor }}
      {...props}
    >
      <CardContent className="p-4 flex flex-col gap-2">{children}</CardContent>
    </Card>
  )
}
