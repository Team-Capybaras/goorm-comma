'use client'

import Image from 'next/image'

export default function Back() {
  return (
    <div className="absolute left-5 top-5 z-10">
      <button
        className="flex items-center justify-center shadow-[0_2px_4px_0_rgba(56,57,56,0.2)] bg-white  p-0 w-[40px] h-[40px] rounded-full cursor-pointer"
        onClick={() => history.back()}
      >
        <Image src="/images/icons/arrow/left.svg" width={24} height={40} alt="뒤로" />
      </button>
    </div>
  )
}
