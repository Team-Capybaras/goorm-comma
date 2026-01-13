import {Weekday} from "@/shared/types/chart-types";

const DAY_LABEL: Record<Weekday, string> = {
  monday: '월',
  tuesday: '화',
  wednesday: '수',
  thursday: '목',
  friday: '금',
  saturday: '토',
  sunday: '일',
}

type WeekdayTabsProps = {
  value: Weekday
  onChange: (day: Weekday) => void,
  activeStyle: string,
  defaultStyle: string,
}

export default function WeekdayTabs({
  value,
  onChange,
  activeStyle,
  defaultStyle,
  }: WeekdayTabsProps) {
  return (
    <div className="flex justify-between mt-2 px-4">
      {(Object.keys(DAY_LABEL) as Weekday[]).map((day) => {
        const isActive = value === day

        return (
          <button
            key={day}
            onClick={() => onChange(day)}
            className={`
              w-[28px] h-[28px] rounded-full font-2xs
              ${isActive
                ? activeStyle
                : defaultStyle
              } 
            `}
          >
            {DAY_LABEL[day]}
          </button>
        )
      })}
    </div>
  )
}