'use client'

import { ReactNode, useEffect, useState } from 'react'
import cn from '@/shared/utils/cn'

export type ToastType = 'default' | 'success' | 'error' | 'info'

export interface ToastProps {
  id: string
  children: ReactNode
  className?: string
  as?: React.ElementType
  leftIcon?: ReactNode
  rightIcon?: ReactNode
  variant?: ToastType
  duration?: number
}

interface SingleToastProps extends ToastProps {
  onClose: (id: string) => void
}

const baseStyle = 'flex items-center justify-center gap-2 px-4 py-3 rounded-full shadow text-white'

const variantClasses: Record<ToastType, string> = {
  default: 'bg-toast text-white',
  success: 'bg-green-500 text-white',
  error: 'bg-red-500 text-white',
  info: 'bg-blue-500 text-white',
}

export default function Toast({
    id,
    children,
    className,
    as: Comp = 'div',
    leftIcon,
    rightIcon,
    variant = 'info',
    duration = 2000,
    onClose,
    ...props
  }: SingleToastProps) {
  const [isVisible, setIsVisible] = useState(false)

  useEffect(() => {
    setIsVisible(true)
    const timer = setTimeout(() => {
      setIsVisible(false)
      setTimeout(() => onClose(id), 300)
    }, duration)
    return () => clearTimeout(timer)
  }, [duration, id, onClose])

  return (
    <Comp
      className={cn(
        baseStyle,
        variantClasses[variant],
        'transition-all duration-300',
        isVisible ? 'translate-y-0 opacity-100' : 'translate-y-5 opacity-0',
        className
      )}
      {...props}
    >
      {leftIcon && leftIcon}
      {children}
      {rightIcon && rightIcon}
    </Comp>
  )
}
