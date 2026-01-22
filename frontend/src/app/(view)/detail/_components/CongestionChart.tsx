'use client'

import {useState} from "react";
import {CongestionType, Weekday} from "@/shared/types/chart-types";
import {congestionDatasets} from "@/app/(view)/detail/_utils/chart-datasets";
import {legendMarginPlugin, tooltipBubblePlugin, verticalLinePlugin} from "@/app/(view)/detail/_utils/chart-plugins";
import LineChart from "@/components/chart/LineChart";
import {congestionLineOptions} from "@/app/(view)/detail/_utils/chart-option";
import WeekdayTabs from "@/components/chart/WeekdayTabs";
import {formatDateTimePad, getWeekday} from "@/shared/utils/time-format";

interface Props {
  data : CongestionType
}

export default function CongestionChart({data}: Props) {
  const [selectedDay, setSelectedDay] = useState<Weekday>(getWeekday())
  const labels = Array.from({ length: 10 }, (_, i) => String(6 + i * 2))

  const dataset = congestionDatasets({
    day: selectedDay,
    weekdays: data.weekdays,
    labels,
  })

  const plugins = (() => {
    const selectedData = data.weekdays.find(
      w => w.weekday === selectedDay
    )
    const recommendedHour = selectedData?.recommendedVisitHour

    const base = [legendMarginPlugin(46)]
    const today = getWeekday()

    if (recommendedHour === undefined) return base
    if (recommendedHour < 9) return base
    if (recommendedHour > 22) return base

    return [
      ...base,
      tooltipBubblePlugin({
        hour: recommendedHour,
        text: (hour: number) =>
          selectedDay === today
            ? `오늘 ${hour}시대가 방문하기 좋아요.`
            : `${hour}시대가 방문하기 좋아요.`,
      }),
      verticalLinePlugin({
        hour: recommendedHour,
      }),
    ]
  })()

  return (
    <>
      <div className="flex justify-between items-center">
        <h3 className="text-body-1-sb">혼잡도</h3>
        <p className="text-caption-3-m text-gray-300">{formatDateTimePad(data.refreshTime)} 기준</p>
      </div>
      <div className="border-1-line-blue p s-4 rounded-2xl mt s-4">
        <div>
          <LineChart
            chartKey={selectedDay}
            labels={labels}
            datasets={dataset}
            plugins={plugins}
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
    </>
  )
}