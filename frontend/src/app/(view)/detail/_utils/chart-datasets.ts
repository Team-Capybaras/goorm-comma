import { ChartDataset } from 'chart.js'
import {TransitionSet, Weekday} from "@/shared/types/chart-types";

type BuildCongestionDatasetsParams = {
  day: string
  transition: Record<Weekday, TransitionSet>
  labels: string[]
}

export function congestionDatasets({
    day,
    transition,
    labels,
  }: BuildCongestionDatasetsParams) {

  const currentHour = new Date().getHours()

  const currentIndex = labels.findIndex(
    (label) => Number(label) >= currentHour
  )

  const mergedData = labels.map((_, index) => {
    if (index < currentIndex) {
      return Number(transition[day].now[index])
    }
    if (index === currentIndex) {
      return Number(transition[day].now[index])
    }
    return Number(transition[day].future[index])
  })

  const createRealtimeGradient = (ctx: CanvasRenderingContext2D, chartArea: any) => {
    const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom)

    gradient.addColorStop(0, 'rgba(95,152,254,0.35)') // 위 진하게
    gradient.addColorStop(1, 'rgba(95,152,254,0.01)') // 아래 연하게

    return gradient
  }

  return [
    {
      label: '과거 방문추이',
      data: transition[day].past.map(Number),
      borderColor: '#BBBCBB',
      borderWidth: 1,
      order: 3,
      tension: 0.4,
      pointRadius: 0,
      fill: true,
      backgroundColor: 'rgba(214,214,214,0.25)',
    },
    {
      label: '실시간 방문추이',
      data: mergedData,
      tension: 0.4,
      borderColor: '#5F98FE',
      borderWidth: 1,
      order:1,
      pointRadius: 0,
      fill: true,
      backgroundColor: (context) => {
        const { chart } = context
        const { ctx, chartArea } = chart

        if (!chartArea) return null

        return createRealtimeGradient(ctx, chartArea)
      },

      segment: {
        borderDash: (ctx) =>
          ctx.p0DataIndex >= currentIndex ? [6, 4] : undefined,

        backgroundColor: (ctx) =>
          ctx.p0DataIndex >= currentIndex
            ? 'rgba(0,0,0,0)'
            : undefined,
      },
    },
    {
      label: '예측 추이',
      data: [],
      order:2,
      borderColor: '#5F98FE',
      borderDash: [6, 4],
      borderWidth: 1,
      pointRadius: 0,
    },
  ]
}