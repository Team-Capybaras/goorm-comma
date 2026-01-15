import {AirIndexGrade} from "@/shared/types/air-types";

export function getAirIndexGrade(level?: number): AirIndexGrade | null {
  if (typeof level !== 'number') return null

  if (level <= 50) return AirIndexGrade.GOOD
  if (level <= 100) return AirIndexGrade.NORMAL
  if (level <= 250) return AirIndexGrade.BAD

  return AirIndexGrade.VERY_BAD
}

export const AIR_INDEX_COLOR_MAP: Record<AirIndexGrade, string> = {
  [AirIndexGrade.GOOD]: 'text-green-400',
  [AirIndexGrade.NORMAL]: 'text-yellow-400',
  [AirIndexGrade.BAD]: 'text-orange-400',
  [AirIndexGrade.VERY_BAD]: 'text-red-400',
}