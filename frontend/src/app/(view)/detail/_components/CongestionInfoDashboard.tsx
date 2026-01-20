'use client'

import { api } from "@/shared/libs/axios";
import CongestionChart from "@/app/(view)/detail/_components/CongestionChart";
import { CongestionType } from "@/shared/types/chart-types";
import ErrorComponent from "@/components/ui/ErrorComponent";
import { useEffect, useState } from "react";

interface CongestionInfoDashboardProps {
  areaCode: string;
}

export default function CongestionInfoDashboard({
    areaCode,
  }: CongestionInfoDashboardProps) {
  const [data, setData] = useState<CongestionType | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(false)

  useEffect(() => {
    if (!areaCode) return

    const fetchData = async () => {
      try {
        setLoading(true)
        const res = await api.get(`/v1/avoidance/statistics`, {
          params: {
            area_code: areaCode,
          },
        })
        setData(res.data.data)
      } catch (err) {
        console.error(err)
        setError(true)
      } finally {
        setLoading(false)
      }
    }

    fetchData()
  }, [areaCode])

  if (loading) {
    return (
      <div className="mt s-6">
        <h3 className="text-body-1-sb">혼잡도</h3>
        {/* 필요하면 Skeleton */}
      </div>
    )
  }

  if (error || !data) {
    return (
      <div className="mt s-6">
        <h3 className="text-body-1-sb">혼잡도</h3>
        <ErrorComponent className="mt s-4" />
      </div>
    )
  }

  return (
    <div className="mt s-6">
      <CongestionChart data={data} />
    </div>
  )
}
