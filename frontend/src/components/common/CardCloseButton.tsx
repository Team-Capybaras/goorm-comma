'use client'

import { MouseEvent } from 'react'
import Image from 'next/image'

interface CardCloseButtonProps {
  onClose?: (event: MouseEvent<HTMLButtonElement>) => void
  className?: string
}

export function CardCloseButton({ onClose, className }: CardCloseButtonProps) {
  return (
    <button
      type="button"
      onClick={(e) => {
        e.stopPropagation()
        onClose?.(e)
      }}
      className={`flex items-center justify-center w-10 h-10 bg-white rounded-full shadow-md hover:bg-gray-50 transition-colors ${className}`}
      aria-label="닫기"
    >
      <Image src="/images/icons/close.svg" alt="닫기" width={20} height={20} />
    </button>
  )
}
