'use client'

import { useEffect } from 'react'
import { useLocationStore } from '@/store/location.store'

export default function AppInitializer() {
  const initLocation = useLocationStore((s) => s.initLocation)

  useEffect(() => {
    initLocation()
  }, [initLocation])

  return null
}