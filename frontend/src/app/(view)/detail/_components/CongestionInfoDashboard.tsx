'use client'

import {useState} from "react";
import LineChart from "@/components/chart/LineChart";
import {congestionLineOptions} from "@/app/(view)/detail/_utils/chart-option";
import {legendMarginPlugin, tooltipBubblePlugin, verticalLinePlugin} from "@/app/(view)/detail/_utils/chart-plugins";
import WeekdayTabs from "@/components/chart/WeekdayTabs";
import {congestionDatasets} from "@/app/(view)/detail/_utils/chart-datasets";
import {TransitionSet, Weekday} from "@/shared/types/chart-types";

type Congestion = {
  refresh_time: string
  predict: string
  transition: Record<Weekday, TransitionSet>
}

type CongestionProps = {
  data: Congestion
}

export default function CongestionInfoDashboard({data}: CongestionProps) {
  const [selectedDay, setSelectedDay] = useState<Weekday>('monday')
  const labels = ['6', '8', '10', '12', '14', '16', '18', '20', '22']
  const dataset = congestionDatasets({
    day: selectedDay,
    transition: data.transition,
    labels,
  })

  return (
    <div className="mt s-6">
      <div className="flex justify-between items-center">
        <h3 className="text-body-1-sb">혼잡도</h3>
        <p className="text-caption-3-m text-gray-300">{data?.refresh_time}</p>
      </div>
      <div className="border-1-line-default p s-4 rounded-xl mt s-4">
        <div>
          <LineChart
            labels={labels}
            datasets={dataset}
            plugins={[
              legendMarginPlugin(46),
              tooltipBubblePlugin({labels, hour: Number(data.predict), text: (hour) => `오늘 ${hour}시간대가 가장 한적해요`}),
              verticalLinePlugin({labels, hour : Number(data.predict)})
            ]}
            options={congestionLineOptions}
          />
        </div>
        <WeekdayTabs
          value={selectedDay}
          onChange={setSelectedDay}
          activeStyle={'bg-blue-100 text-white text-blue-600'}
          defaultStyle={'bg-white text-sub border-1-line-default'}
        />
      </div>
    </div>
  )
}