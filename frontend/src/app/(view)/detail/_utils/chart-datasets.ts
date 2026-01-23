import {ChartDataset, ChartType, ScriptableContext, ScriptableLineSegmentContext} from 'chart.js'
import {CongestionWeekday, Weekday, WEEKDAYS} from "@/shared/types/chart-types";
import {getWeekday} from "@/shared/utils/time-format";

type BuildCongestionDatasetsParams = {
  day: string
  weekdays: CongestionWeekday[]
  labels: string[]
}

export function congestionDatasets({
    day,
    weekdays,
    labels,
  }: BuildCongestionDatasetsParams) {
  const currentHour = new Date().getHours()
  const currentDay: Weekday = getWeekday()

  // 0시를 24시로 표기
  const normalizeHourForChart = (hour: number) =>
    hour === 0 ? 24 : hour

  const targetDay = weekdays.find(w => w.weekday === day)
  if (!targetDay) return []

  const hourMap = new Map(
    targetDay.hours.map(h => [
      normalizeHourForChart(h.hour),
      h,
    ])
  )

  /* ---------- 과거 ---------- */
  const pastData = labels.map(label => {
    const hour = Number(label)
    return {
      x: hour,
      y: hourMap.get(hour)?.past ?? null,
    }
  })

  /* ---------- 실시간 + 미래 병합 ---------- */
  const realtimeMerged = labels.map(label => {
    const hour = Number(label)
    const data = hourMap.get(hour)

    if (!data) return { x: hour, y: null }
    if (hour <= currentHour && day === currentDay) return { x: hour, y: data.now ?? 0 }
    return { x: hour, y: data.future }
  })

  /* ---------- gradient ---------- */
  const createRealtimeGradient = (
    ctx: CanvasRenderingContext2D,
    chartArea: any
  ) => {
    const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom)
    gradient.addColorStop(0, 'rgba(95,152,254,0.35)')
    gradient.addColorStop(1, 'rgba(95,152,254,0.01)')
    return gradient
  }

  return [
    {
      label: '과거 방문추이',
      data: pastData,
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
      data: realtimeMerged,
      tension: 0.4,
      borderColor: '#5F98FE',
      borderWidth: 1,
      order:1,
      pointRadius: 0,
      fill: true,
      backgroundColor: (context: ScriptableContext<ChartType>) => {
        const { chart } = context
        if (!chart.chartArea) return undefined
        return createRealtimeGradient(chart.ctx, chart.chartArea)
      },
      segment: {
        borderDash: (ctx: ScriptableLineSegmentContext) =>
          Number(labels[ctx.p0DataIndex]) >= currentHour
            ? [3, 3]
            : undefined,
        backgroundColor: (ctx: ScriptableLineSegmentContext) =>
          Number(labels[ctx.p0DataIndex]) >= currentHour
            ? 'rgba(0,0,0,0)'
            : undefined,
      },
    },
    {
      label: '예측 추이',
      data: [],
      order:2,
      borderColor: '#5F98FE',
      borderDash: [1.5, 1.5],
      borderWidth: 1,
      pointRadius: 0,
    },
  ]
}