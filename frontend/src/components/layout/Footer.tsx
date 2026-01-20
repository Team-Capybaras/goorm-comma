'use client'

import Link from 'next/link'
import Image from 'next/image'

export default function Footer() {
  return (
    <footer className="w-full bg-[#FBFBFB] px-6 pt-6 pb-20 flex flex-col justify-start">
      <div className="flex items-center gap-[10px] mb-[14px]">
        <Link
          href="/"
          className="text-[11px] font-medium text-[#70716F] leading-[1.5] tracking-[-0.01em] hover:text-gray-900 transition-colors"
        >
          위치정보 이용약관
        </Link>

        <span className="text-[10px] text-[#E8E8E8]">|</span>

        <Link
          href="/"
          className="text-[11px] font-semibold text-[#70716F] leading-[1.5] tracking-[-0.01em] hover:text-gray-900 transition-colors"
        >
          개인정보 처리방침
        </Link>
      </div>

      <div className="flex items-center gap-2">
        <div className="relative w-6 h-6 rounded-md overflow-hidden">
          <Image src="/logo/logo.svg" alt="파키바라 로고" fill className="object-contain" />
        </div>

        <span className="text-sm font-bold text-[#515251]">파키바라</span>
      </div>
    </footer>
  )
}
