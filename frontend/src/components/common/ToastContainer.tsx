'use client'

import { useState, useCallback } from 'react'
import Toast, { ToastType } from './Toast'

export default function useToast() {
  const [toasts, setToasts] = useState<{
    id: string
    children: React.ReactNode
    variant?: ToastType
    duration?: number
  }[]>([])

  const showToast = useCallback((
    children: React.ReactNode,
    variant: ToastType = 'default',
    duration: number = 2000
  ) => {
    if (toasts.length > 0) return

    const id = crypto.randomUUID()
    setToasts([{ id, children, variant, duration }])
  }, [toasts])

  const removeToast = (id: string) => {
    setToasts(prev => prev.filter(t => t.id !== id))
  }

  const ToastContainer = (
    <div className="fixed bottom-25 left-1/2 -translate-x-1/2 z-[999] min-w-40 w-100 flex flex-col gap-2">
      {toasts.map(t => (
        <Toast
          key={t.id}
          id={t.id}
          variant={t.variant}
          duration={t.duration}
          onClose={() => removeToast(t.id)}
        >
          {t.children}
        </Toast>
      ))}
    </div>
  )

  return { showToast, ToastContainer }
}
