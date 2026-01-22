'use client'

import { api } from "@/shared/libs/axios";
import CongestionChart from "@/app/(view)/detail/_components/CongestionChart";
import { CongestionType } from "@/shared/types/chart-types";
import ErrorComponent from "@/components/ui/ErrorComponent";
import { useEffect, useState } from "react";
import CongestionInfoDashboardSkeleton from "@/app/(view)/detail/_status/CongestionInfoDashboardSkeleton";

interface CongestionInfoDashboardProps {
  areaCode: string;
}

const mock = {
  "status": "success",
  "code": 200,
  "message": "공원 혼잡도 통계 조회 성공",
  "data": {
    "areaCode": "POI093",
    "refreshTime": "2026-01-22T17:16:13.462048016",
    "weekdays": [
      {
        "weekday": "MON",
        "today": false,
        "recommendedVisitHour": 13,
        "hours": [
          {
            "hour": 0,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 1,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 2,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 3,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 4,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 5,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 6,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 7,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 8,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 9,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 10,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 11,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 12,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 13,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 14,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 15,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 16,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 17,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 18,
            "past": 2250,
            "now": null,
            "future": 2750
          },
          {
            "hour": 19,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 20,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 21,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 22,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 23,
            "past": 1500,
            "now": null,
            "future": 2000
          }
        ]
      },
      {
        "weekday": "TUE",
        "today": false,
        "recommendedVisitHour": 15,
        "hours": [
          {
            "hour": 0,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 1,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 2,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 3,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 4,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 5,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 6,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 7,
            "past": 1875,
            "now": null,
            "future": 2375
          },
          {
            "hour": 8,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 9,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 10,
            "past": 2625,
            "now": null,
            "future": 3125
          },
          {
            "hour": 11,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 12,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 13,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 14,
            "past": 1375,
            "now": null,
            "future": 1875
          },
          {
            "hour": 15,
            "past": 1375,
            "now": null,
            "future": 1875
          },
          {
            "hour": 16,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 17,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 18,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 19,
            "past": 1875,
            "now": null,
            "future": 2375
          },
          {
            "hour": 20,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 21,
            "past": 1375,
            "now": null,
            "future": 1875
          },
          {
            "hour": 22,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 23,
            "past": 1875,
            "now": null,
            "future": 2375
          }
        ]
      },
      {
        "weekday": "WED",
        "today": false,
        "recommendedVisitHour": 14,
        "hours": [
          {
            "hour": 0,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 1,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 2,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 3,
            "past": 1375,
            "now": null,
            "future": 1875
          },
          {
            "hour": 4,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 5,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 6,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 7,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 8,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 9,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 10,
            "past": 2600,
            "now": null,
            "future": 3100
          },
          {
            "hour": 11,
            "past": 2250,
            "now": null,
            "future": 2750
          },
          {
            "hour": 12,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 13,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 14,
            "past": 1437,
            "now": null,
            "future": 1937
          },
          {
            "hour": 15,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 16,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 17,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 18,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 19,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 20,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 21,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 22,
            "past": 1625,
            "now": null,
            "future": 2125
          },
          {
            "hour": 23,
            "past": 2250,
            "now": null,
            "future": 2750
          }
        ]
      },
      {
        "weekday": "THU",
        "today": true,
        "recommendedVisitHour": 17,
        "hours": [
          {
            "hour": 0,
            "past": 2666,
            "now": 1250,
            "future": 3166
          },
          {
            "hour": 1,
            "past": 2500,
            "now": 1000,
            "future": 3000
          },
          {
            "hour": 2,
            "past": 2000,
            "now": 1000,
            "future": 2500
          },
          {
            "hour": 3,
            "past": 1750,
            "now": 1000,
            "future": 2250
          },
          {
            "hour": 4,
            "past": 1750,
            "now": 1000,
            "future": 2250
          },
          {
            "hour": 5,
            "past": 2000,
            "now": 1000,
            "future": 2500
          },
          {
            "hour": 6,
            "past": 2250,
            "now": 1250,
            "future": 2750
          },
          {
            "hour": 7,
            "past": 2250,
            "now": 1750,
            "future": 2750
          },
          {
            "hour": 8,
            "past": 2250,
            "now": 2750,
            "future": 2750
          },
          {
            "hour": 9,
            "past": 2250,
            "now": 3250,
            "future": 2750
          },
          {
            "hour": 10,
            "past": 2250,
            "now": 2500,
            "future": 2750
          },
          {
            "hour": 11,
            "past": 2000,
            "now": 2000,
            "future": 2500
          },
          {
            "hour": 12,
            "past": 1750,
            "now": 1750,
            "future": 2250
          },
          {
            "hour": 13,
            "past": 1500,
            "now": 1500,
            "future": 2000
          },
          {
            "hour": 14,
            "past": 1500,
            "now": 1750,
            "future": 2000
          },
          {
            "hour": 15,
            "past": 1250,
            "now": 1750,
            "future": 1750
          },
          {
            "hour": 16,
            "past": 1000,
            "now": 2000,
            "future": 1500
          },
          {
            "hour": 17,
            "past": 1000,
            "now": 2000,
            "future": 1500
          },
          {
            "hour": 18,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 19,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 20,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 21,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 22,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 23,
            "past": 2750,
            "now": null,
            "future": 3250
          }
        ]
      },
      {
        "weekday": "FRI",
        "today": false,
        "recommendedVisitHour": 17,
        "hours": [
          {
            "hour": 0,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 1,
            "past": 2250,
            "now": null,
            "future": 2750
          },
          {
            "hour": 2,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 3,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 4,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 5,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 6,
            "past": 2250,
            "now": null,
            "future": 2750
          },
          {
            "hour": 7,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 8,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 9,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 10,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 11,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 12,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 13,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 14,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 15,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 16,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 17,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 18,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 19,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 20,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 21,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 22,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 23,
            "past": 1000,
            "now": null,
            "future": 1500
          },
        ]
      },
      {
        "weekday": "SAT",
        "today": false,
        "recommendedVisitHour": 9,
        "hours": [
          {
            "hour": 0,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 1,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 2,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 3,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 4,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 5,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 6,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 7,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 8,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 9,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 10,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 11,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 12,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 13,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 14,
            "past": 3250,
            "now": null,
            "future": 3750
          },
          {
            "hour": 15,
            "past": 3750,
            "now": null,
            "future": 4250
          },
          {
            "hour": 16,
            "past": 4000,
            "now": null,
            "future": 4500
          },
          {
            "hour": 17,
            "past": 3750,
            "now": null,
            "future": 4250
          },
          {
            "hour": 18,
            "past": 3250,
            "now": null,
            "future": 3750
          },
          {
            "hour": 19,
            "past": 2250,
            "now": null,
            "future": 2750
          },
          {
            "hour": 20,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 21,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 22,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 23,
            "past": 1500,
            "now": null,
            "future": 2000
          }
        ]
      },
      {
        "weekday": "SUN",
        "today": false,
        "recommendedVisitHour": 22,
        "hours": [
          {
            "hour": 0,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 1,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 2,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 3,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 4,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 5,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 6,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 7,
            "past": 1000,
            "now": null,
            "future": 1500
          },
          {
            "hour": 8,
            "past": 1250,
            "now": null,
            "future": 1750
          },
          {
            "hour": 9,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 10,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 11,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 12,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 13,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 14,
            "past": 2750,
            "now": null,
            "future": 3250
          },
          {
            "hour": 15,
            "past": 3750,
            "now": null,
            "future": 4250
          },
          {
            "hour": 16,
            "past": 3750,
            "now": null,
            "future": 4250
          },
          {
            "hour": 17,
            "past": 3000,
            "now": null,
            "future": 3500
          },
          {
            "hour": 18,
            "past": 2500,
            "now": null,
            "future": 3000
          },
          {
            "hour": 19,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 20,
            "past": 2000,
            "now": null,
            "future": 2500
          },
          {
            "hour": 21,
            "past": 1750,
            "now": null,
            "future": 2250
          },
          {
            "hour": 22,
            "past": 1500,
            "now": null,
            "future": 2000
          },
          {
            "hour": 23,
            "past": 1500,
            "now": null,
            "future": 2000
          }
        ]
      }
    ]
  },
  "errors": null
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
        setData(mock.data)
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
        <CongestionInfoDashboardSkeleton />
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
