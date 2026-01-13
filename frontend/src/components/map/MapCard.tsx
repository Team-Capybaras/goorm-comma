'use client'

import { ComponentProps } from 'react'
import { Card, CardContent } from '@/components/common/Card'
import { getCongestionColorValue } from '@/shared/utils/map-helpers'
import { CongestionLevel } from '@/shared/types/map-types'
import cn from '@/shared/utils/cn'

interface MapCardProps extends ComponentProps<typeof Card> {
  congestion?: CongestionLevel
  borderColor?: string
  children: React.ReactNode
}

export default function MapCard({
  congestion,
  borderColor,
  className,
  children,
  ...props
}: MapCardProps) {
  const finalBorderColor = congestion ? getCongestionColorValue(congestion) : borderColor

  return (
    <Card
      className={cn('border-[1px] rounded-[20px] shadow-sm transition-colors bg-white', className)}
      style={{ borderColor: finalBorderColor }}
      {...props}
    >
      <CardContent className="pt-4 px-4 pb-5 flex flex-col gap-2">{children}</CardContent>
    </Card>
  )
}
