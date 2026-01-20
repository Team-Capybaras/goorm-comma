export default function EnvironmentSkeleton() {
  return (
    <div className="animate-pulse">
      <div className="border-1-line-default rounded-7 p s-4 mt s-4">
        {/* top row */}
        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <div className="w-[24px] h-[24px] bg-gray-200 rounded-full mr s-3" />
            <div className="h-[16px] w-[48px] bg-gray-200 rounded mr s-2" />
            <div className="h-[16px] w-[32px] bg-gray-200 rounded" />
          </div>

          <div className="w-[1px] h-[16px] bg-gray-100" />

          <div className="flex items-center">
            <div className="h-[14px] w-[72px] bg-gray-200 rounded mr s-4" />
            <div className="h-[14px] w-[48px] bg-gray-200 rounded" />
          </div>
        </div>

        {/* divider */}
        <div className="w-full h-[1px] my-4 bg-gray-100" />

        {/* bottom row */}
        <div className="flex justify-between items-center">
          <div className="flex items-center">
            <div className="w-[24px] h-[24px] bg-gray-200 rounded-full" />
            <div className="h-[14px] w-[90px] bg-gray-200 rounded ml s-3" />
          </div>

          <div className="h-[16px] w-[80px] bg-gray-200 rounded" />
        </div>
      </div>
    </div>
  )
}
