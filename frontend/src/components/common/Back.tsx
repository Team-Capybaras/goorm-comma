'use client'

export default function Back() {
  return (
    <div className="fixed left-5 top-5 ">
      <button
        className="flex items-center justify-center shadow-[0_2px_4px_0_rgba(56,57,56,0.2)] bg-white  p-0 w-[40px] h-[40px] rounded-full cursor-pointer"
        onClick={() => history.back()}
      >
        <img src="/images/icons/arrow/left.svg" className="w-6 h-10" alt="뒤로"/>
      </button>
    </div>
  )
}