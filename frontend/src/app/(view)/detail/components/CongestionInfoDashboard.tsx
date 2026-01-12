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
} from 'chart.js'
import {useState} from "react";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend
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

  const dataset = [{
    id: selectedDay,
    label: `실시간 방문 추이`,
    data: data.transition[selectedDay].now.map(Number),
    tension: 0.4,
    borderWidth: 2,
    borderColor: '#5F98FE',
    backgroundColor: 'rgba(59, 130, 246, 0.15)',
    fill: true,
    pointRadius: 0,
  },
    {
      id: selectedDay,
      label: `과거 방문 추이`,
      data: data.transition[selectedDay].past.map(Number),
      tension: 0.4,
      borderWidth: 2,
      borderColor: '#D6D6D6',
      backgroundColor: 'rgba(59, 130, 246, 0.15)',
      fill: true,
      pointRadius: 0,
    },
    {
      id: selectedDay,
      label: `예측 추이`,
      data: data.transition[selectedDay].future.map(Number),
      tension: 0.4,
      borderWidth: 2,
      borderColor: '#3B82F6',
      backgroundColor: 'rgba(59, 130, 246, 0.15)',
      fill: true,
      pointRadius: 0,
    }
  ]

  return (
    <div className="mt-8">
      <div className="flex justify-between items-end">
        <h3 className="font-sb">혼잡도</h3>
        <p className="font-3xs text-gray-400">{data?.refresh_time}</p>
      </div>
      <div className="border-1-line-default p-4 rounded-xl mt-3">
        <div>
          <Line
            datasetIdKey="id"
            data={{
              labels,
              datasets: dataset,
            }}
            options={{
              responsive: true,
              maintainAspectRatio: false,
              plugins: {
                legend: {display: true},
              },
              scales: {
                y: {
                  grid: {
                    display: true,
                    drawBorder: false ,
                  },
                  ticks: {
                    display: false,
                  },
                },
                x: {
                  grid: {display: false},
                },
              },
              animation: {
                duration: 300,
              },
            }}
            height={180}
          />
        </div>
        <div className="flex justify-between mt-2 px-1">
          {(Object.keys(DAY_LABEL) as Weekday[]).map((day) => (
            <button
              key={day}
              onClick={() => setSelectedDay(day)}
              className={`
                w-[28px] h-[28px] rounded-full border text-sm font-sb
                transition-all
                ${
                selectedDay === day
                  ? 'bg-blue-500 text-white border-blue-500'
                  : 'bg-white text-gray-500 border-gray-200'
              }
              `}
            >
              {DAY_LABEL[day]}
            </button>
          ))}
        </div>
      </div>
      <div className="py-3 bg-blue-0 border-1-line-blue mt-5">
        <div className="flex items-center justify-center">
          <Image src={"/images/icons/crowd.svg"} className="mr-3" width={16} height={16} alt={"사람 아이콘"}/>
          <p className="text-blue-500 font-sb font-xs">오늘 {data?.predict}시가 가장 한적해요</p>
        </div>
      </div>
    </div>
  )
}