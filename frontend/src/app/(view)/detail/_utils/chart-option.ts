import { ChartOptions } from 'chart.js'

export const congestionLineOptions: ChartOptions<'line'> = {
  responsive: true,
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
      type: 'linear',
      min: 6,
      max: 24,
      ticks: {
        font: {
          size: 8,
          weight: 500,
        },
        color: '#D6D6D6',
        stepSize: 2,
        callback: (value) => {
          if (value === 24) return '24'
          return String(value)
        },
      },
      grid: {
        display: false,
      },
    },
    y: {
      border: {
        display: false,
      },
      ticks: {
        display: false,
        count: 5,
      },
      grid: {
        drawTicks: false,
        color: '#E8E8E8',
      },
    },
  },
}