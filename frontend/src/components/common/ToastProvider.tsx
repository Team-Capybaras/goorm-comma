'use client'

import { createContext, useContext } from 'react'
import useToast from '@/components/common/ToastContainer'

type ToastFn = (message: string, type?: any, duration?: number) => void

const ToastContext = createContext<ToastFn | null>(null)

export function ToastProvider({ children }: { children: React.ReactNode }) {
  const { showToast, ToastContainer } = useToast()

  return (
    <ToastContext.Provider value={showToast}>
      {children}
      {ToastContainer}
    </ToastContext.Provider>
  )
}

export function useGlobalToast() {
  const ctx = useContext(ToastContext)
  if (!ctx) {
    throw new Error('useGlobalToast must be used within ToastProvider')
  }
  return ctx
}