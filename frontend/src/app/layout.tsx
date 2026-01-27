import type { Metadata } from 'next'
import Script from 'next/script'
import './globals.css'
import React from 'react'
import AppInitializer from '@/providers/AppInitializer'
import { ToastProvider } from '@/components/common/ToastProvider'

export const metadata: Metadata = {
  title: '파키바라 | parkybara',
  description: '파키바라 | parkybara',
  icons: {
    icon: '/logo/logo-color.png',
  },
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en">
      <body>
        <AppInitializer />
        <Script
          src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAO_MAP_KEY}&libraries=services,clusterer&autoload=false`}
          strategy="afterInteractive"
        />
        <ToastProvider>
          {children}
        </ToastProvider>
      </body>
    </html>
  )
}
