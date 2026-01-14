'use client'

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
  ChartOptions,
  Plugin,
} from 'chart.js'

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
  Filler
)

type LineChartProps = {
  labels: string[]
  datasets: any[]
  options?: ChartOptions<'line'>
  plugins?: Plugin<'line'>[]
  height?: number
}

export default function LineChart({
    labels,
    datasets,
    options,
    plugins = [],
    height = 180,
  }: LineChartProps) {
  return (
    <Line
      datasetIdKey="id"
      data={{ labels, datasets }}
      options={options}
      plugins={plugins}
      height={height}
    />
  )
}
