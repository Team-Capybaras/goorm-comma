'use client'

import Image from 'next/image'
import { Line } from 'react-chartjs-2'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
  Filler,
} from 'chart.js'
import {useState} from "react";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
  Filler
)

type Weekday =
  | 'monday'
  | 'tuesday'
  | 'wednesday'
  | 'thursday'
  | 'friday'
  | 'saturday'
  | 'sunday'

type TransitionSet = {
  past: string[]
  now: string[]
  future: string[]
}

type Congestion = {
  refresh_time: string
  predict: string
  transition: Record<Weekday, TransitionSet>
}

type CongestionProps = {
  data: Congestion
}

const DAY_LABEL: Record<Weekday, string> = {
  monday: '월',
  tuesday: '화',
  wednesday: '수',
  thursday: '목',
  friday: '금',
  saturday: '토',
  sunday: '일',
}

export default function CongestionInfoDashboard({data}: CongestionProps) {
  const [selectedDay, setSelectedDay] = useState<Weekday>('monday')

  const labels = ['6', '8', '10', '12', '14', '16', '18', '20', '22']

  const currentHour = new Date().getHours()

  const currentIndex = labels.findIndex(
    (label) => Number(label) >= currentHour
  )

  const mergedData = labels.map((_, index) => {
    if (index < currentIndex) {
      return Number(data.transition[selectedDay].now[index])
    }
    if (index === currentIndex) {
      return Number(data.transition[selectedDay].now[index])
    }
    return Number(data.transition[selectedDay].future[index])
  })

  const createRealtimeGradient = (ctx: CanvasRenderingContext2D, chartArea: any) => {
    const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom)

    gradient.addColorStop(0, 'rgba(95,152,254,0.35)') // 위 진하게
    gradient.addColorStop(1, 'rgba(95,152,254,0.01)') // 아래 연하게

    return gradient
  }

  const dataset = [
    {
      label: '과거 방문추이',
      data: data.transition[selectedDay].past.map(Number),
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

  const predictHour = Number(data.predict) // 17
  const predictIndex = labels.findIndex(
    (label) => Number(label) === predictHour
  )

  const tooltipBubblePlugin = {
    id: 'tooltipBubble',
    afterDraw(chart: any) {
      const { ctx, scales } = chart
      const x = (() => {
        const leftIndex = labels.findIndex(l => Number(l) === 16)
        const rightIndex = labels.findIndex(l => Number(l) === 18)

        const xLeft = scales.x.getPixelForTick(leftIndex)
        const xRight = scales.x.getPixelForTick(rightIndex)

        return (xLeft + xRight) / 2
      })()

      const topY = scales.y.top - 35

      const text = `오늘 ${predictHour}시대가 가장 한적해요`

      // bubble size
      const padding = 10
      ctx.font = '12px Pretendard'
      const textWidth = ctx.measureText(text).width
      const bubbleWidth = textWidth + padding * 2
      const bubbleHeight = 28

      const bubbleX = x - bubbleWidth / 2
      const bubbleY = topY

      ctx.save()

      // bubble
      ctx.fillStyle = '#3B82F6'
      ctx.beginPath()
      ctx.roundRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight, 14)
      ctx.fill()

      // text
      ctx.fillStyle = '#FFFFFF'
      ctx.textAlign = 'center'
      ctx.textBaseline = 'middle'
      ctx.fillText(text, x, bubbleY + bubbleHeight / 2)

      ctx.restore()
    },
  }

  const verticalLinePlugin = {
    id: 'verticalLine',
    afterDraw(chart: any) {
      const { ctx, scales, chartArea } = chart
      if (!chartArea) return

      const xScale = scales.x
      const yScale = scales.y

      const leftIndex = labels.findIndex(l => Number(l) === 16)
      const rightIndex = labels.findIndex(l => Number(l) === 18)

      if (leftIndex === -1 || rightIndex === -1) return

      const xLeft = xScale.getPixelForTick(leftIndex)
      const xRight = xScale.getPixelForTick(rightIndex)
      const x = (xLeft + xRight) / 2

      ctx.save()

      /* =====================
         세로 점선
      ===================== */
      ctx.setLineDash([4, 4])
      ctx.strokeStyle = '#0C4596'
      ctx.lineWidth = 1

      ctx.beginPath()
      ctx.moveTo(x, yScale.top)
      ctx.lineTo(x, yScale.bottom)
      ctx.stroke()

      /* =====================
         위쪽 삼각형
      ===================== */
      const triangleSize = 6
      const triangleY = chartArea.top

      ctx.setLineDash([])
      ctx.fillStyle = '#0C4596'

      ctx.beginPath()
      ctx.moveTo(x - triangleSize, triangleY)
      ctx.lineTo(x + triangleSize, triangleY)
      ctx.lineTo(x, triangleY + triangleSize)
      ctx.closePath()
      ctx.fill()

      ctx.restore()
    },
  }


  const legendMargin = {
    id: 'legendMargin',
    beforeInit(chart, legend, options) {
      const fitValue = chart.legend.fit
      const spacing = 46 // 추가할 간격

      chart.legend.fit = function fit() {
        fitValue.bind(chart.legend)()
        return (this.height += spacing)
      }
    },
  }

  return (
    <div className="mt s-6">
      <div className="flex justify-between items-center">
        <h3 className="text-body-1-sb">혼잡도</h3>
        <p className="text-caption-3-m text-gray-300">{data?.refresh_time}</p>
      </div>
      <div className="border-1-line-default p s-4 rounded-xl mt s-4">
        <div>
          <Line
            datasetIdKey="id"
            data={{
              labels,
              datasets: dataset,
            }}
            plugins={[legendMargin,verticalLinePlugin, tooltipBubblePlugin]}
            options={{
              plugins: {
                legend: {
                  display: true,
                  align: 'end',
                  labels: {
                    color: '#383938',
                    font: {
                      size: 9,
                    },
                    boxWidth: 12,
                    boxHeight: 1,
                    padding: 8,
                    generateLabels: () => [
                      {
                        text: '과거 방문추이',
                        strokeStyle: '#BBBCBB',
                        lineWidth: 1,
                        lineDash: [],
                        borderRadius: 1,
                        fillStyle: '#BBBCBB',
                      },
                      {
                        text: '실시간 방문추이',
                        strokeStyle: '#5F98FE',
                        lineWidth: 2,
                        lineDash: [],
                        borderRadius: 1,
                        fillStyle: '#5F98FE',
                      },
                      {
                        text: '예측 추이',
                        strokeStyle: '#5F98FE',
                        lineWidth: 2,
                        lineDash: [4, 3],
                        fillStyle: 'transparent',
                      },
                    ],
                  },
                },
              },

              scales: {
                x: {
                  ticks: {
                    font: {
                      size: 8,
                      weight: 500,
                      color: '#D6D6D6',
                    }
                  },
                  grid: {
                    display: false,
                    drawBorder: false,
                  },
                },
                y: {
                  border: {
                    display:false
                  },
                  ticks: {
                    display: false,
                    count: 5,
                  },
                  grid: {
                    display: true,
                    drawBorder: false,
                    drawTicks : false,
                    color: '#E8E8E8',
                  },
                },
              }
            }}
            height={180}
          />
        </div>
        <div className="flex justify-between mt-2 px-4">
          {(Object.keys(DAY_LABEL) as Weekday[]).map((day) => (
            <button
              key={day}
              onClick={() => setSelectedDay(day)}
              className={`
                w-[28px] h-[28px] rounded-full font-2xs
                ${
                selectedDay === day
                  ? 'bg-blue-100 text-white text-blue-600'
                  : 'bg-white text-sub border-1-line-default'
              }
              `}
            >
              {DAY_LABEL[day]}
            </button>
          ))}
        </div>
      </div>
    </div>
  )
}