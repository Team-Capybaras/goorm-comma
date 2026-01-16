export type Weekday =
  | 'MON'
  | 'TUE'
  | 'WED'
  | 'THU'
  | 'FRI'
  | 'SAT'
  | 'SUN'

export interface CongestionHour {
  hour: number
  past: number
  now: number | null
  future: number
}

export interface CongestionWeekday {
  weekday: Weekday
  today: boolean
  recommendedVisitHour: number
  hours: CongestionHour[]
}

export interface CongestionType {
  refreshTime: string
  weekdays: CongestionWeekday[]
}