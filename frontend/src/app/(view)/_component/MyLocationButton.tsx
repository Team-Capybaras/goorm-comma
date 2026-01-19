'use client'

import Image from 'next/image'

interface LocationButtonProps {
  label: string
  loading?: boolean
  onClick: () => void
}

export default function MyLocationButton({
  label,
  loading,
  onClick,
}: LocationButtonProps) {
  return (
    // 버튼 클릭 시 현재 위치 불러오기
    <button
      type="button"
      onClick={onClick}
      disabled={loading}
      className="inline-flex items-center gap-1 text-caption-1-m text-sub"
    >
      <Image
        src="/images/icons/current.svg"
        alt="현재 위치"
        width={18}
        height={18}
      />
      {loading ? '위치 찾는 중…' : label}
    </button>
  )
}