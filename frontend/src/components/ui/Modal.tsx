'use client'

import { ComponentPropsWithRef, ReactNode, useEffect, useRef, useState } from 'react'
import { createPortal } from 'react-dom'
import { Card, CardHeader, CardContent, CardFooter } from '@/components/common/Card'
import cn from '@/shared/utils/cn'

interface ModalProps extends ComponentPropsWithRef<'div'> {
  open: boolean // 열림/닫힘 여부
  onClose: () => void // 닫기 함수
  children?: ReactNode // 내용
  size?: 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl' | 'full' // 사이즈 정의
  closeOnBackdrop?: boolean // 배경 클릭시 닫힘 여부
  closeOnEscape?: boolean // ESC 닫기 여부
  zIndex?: number // 페이지 위에 띄우기용 zindex
}

export function Modal({
  open,
  onClose,
  children,
  size = 'md', // 기본 사이즈 md
  closeOnBackdrop = false, // 모달 클릭으로 닫히는 현상 방지
  closeOnEscape = true, // 기본 설정으로 ESC 닫기 가능
  zIndex = 50, // 기본 zIndex 값 설정 (일반 모달은 50)
  className,
  ...props
}: ModalProps) {
  const modalRef = useRef<HTMLDivElement>(null)
  const scrollYRef = useRef<number>(0)
  const [mounted, setMounted] = useState(false)

  // Mount 시 발생
  useEffect(() => {
    setMounted(true)
  }, [])

  // Esc로 닫기 로직
  useEffect(() => {
    // 열렸을 때만 ESC로 닫기 가능.
    if (!open || !closeOnEscape) return

    const handleEscape = (e: KeyboardEvent) => {
      if (e.key === 'Escape') {
        onClose()
      }
    }

    document.addEventListener('keydown', handleEscape)
    return () => document.removeEventListener('keydown', handleEscape)
  }, [open, onClose, closeOnEscape])

  // 모달 띄워질 시, 배경의 스크롤 고정 로직
  useEffect(() => {
    if (open) {
      scrollYRef.current = window.scrollY
      document.body.style.position = 'fixed'
      document.body.style.top = `-${scrollYRef.current}px`
      document.body.style.width = '100%'
    }

    return () => {
      if (open) { // 초기화 로직
        const scrollY = scrollYRef.current
        document.body.style.position = ''
        document.body.style.top = ''
        document.body.style.width = ''
        window.scrollTo(0, scrollY)
      }
    }
  }, [open])

  if (!open || !mounted) return null

  // 사이즈 정의
  const sizeClasses = {
    sm: 'max-w-sm',
    md: 'max-w-md',
    lg: 'max-w-lg',
    xl: 'max-w-xl',
    '2xl': 'max-w-2xl',
    '3xl': 'max-w-3xl',
    full: 'max-w-full',
  }

  const modalContent = (
    <>
      {/* 배경보다 zindex 10 높게 설정 후, 카드를 감쌈. */}
      <div
        style={{ zIndex: zIndex + 10 }}
        className="fixed bottom-0 left-1/2 -translate-x-1/2 z-99 w-full max-w-2xl pt-8 px s-5 py-12 rounded-t-xl bg-bright"
        // 접근성 관련 설정
        role="dialog"
        aria-modal="true"
      >
        {/* Card 컴포넌트 재사용 */}
        <div
          ref={modalRef}
          className={cn(
            sizeClasses[size],
            className
          )}
          {...props}
        >
          {children}
        </div>
      </div>

      {/* 배경 어둡게 설정 */}
      <div
        style={{ zIndex: zIndex }}
        className="fixed w-full h-full top-0 left-0 inset-0 bg-black/40"
        onClick={() => onClose()}
      />
    </>
  )

  return createPortal(modalContent, document.body)
}

// Modal 전용 Header/Content/Footer - 패딩 제거하고 CardHeader/Content/Footer 래핑
export function ModalHeader({ className, ...props }: ComponentPropsWithRef<typeof CardHeader>) {
  return <CardHeader className={cn('p-0', className)} {...props} />
}

export function ModalContent({ className, ...props }: ComponentPropsWithRef<typeof CardContent>) {
  return <CardContent className={cn('p-0', className)} {...props} />
}

export function ModalFooter({ className, ...props }: ComponentPropsWithRef<typeof CardFooter>) {
  return <CardFooter className={cn('p-0', className)} {...props} />
}
