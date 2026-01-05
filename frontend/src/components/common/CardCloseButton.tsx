'use client'

import { MouseEvent } from 'react'
import { X } from 'lucide-react'

interface CardCloseButtonProps {
  onClose?: (event: MouseEvent<HTMLButtonElement>) => void
}

export function CardCloseButton({ onClose }: CardCloseButtonProps) {
  return (
    <button
      type="button"
      onClick={(e) => {
        // 이벤트 버블링 방지 (카드 클릭과 닫기 버튼 클릭 구분)
        e.stopPropagation()
        onClose?.(e)
      }}
      tabIndex={-1}
      // 스타일은 기존에 있던 그대로 가져옴
      className="absolute cursor-pointer right-4 top-4 rounded-sm opacity-70 ring-offset-background transition-opacity hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:pointer-events-none"
      aria-label="닫기"
    >
      <X className="size-4" />
    </button>
  )
}
