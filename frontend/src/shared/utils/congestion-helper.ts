export const CONGESTION_LEVEL = {
  FREE: '여유',
  NORMAL: '보통',
  SLIGHT: '약간 붐빔',
  BUSY: '붐빔',
} as const

export type CongestionLevel =
  typeof CONGESTION_LEVEL[keyof typeof CONGESTION_LEVEL]

export const CONGESTION_COLOR_MAP: Record<CongestionLevel, string> = {
  [CONGESTION_LEVEL.FREE]: 'text-positive',
  [CONGESTION_LEVEL.NORMAL]: 'text-normal',
  [CONGESTION_LEVEL.SLIGHT]: 'text-caution',
  [CONGESTION_LEVEL.BUSY]: 'text-warning',
}