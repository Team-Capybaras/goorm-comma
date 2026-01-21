export default function CongestionInfoDashboardSkeleton() {
  return (
    <div className="border-1-line-default p s-4 rounded-xl mt s-4 animate-pulse">
      {/* chart area */}
      <div className="w-full h-[200px] bg-gray-200 rounded mb s-4" />

      {/* weekday tabs */}
      <div className="flex gap-2">
        {[0, 1, 2, 3, 4, 5, 6].map((i) => (
          <div
            key={i}
            className="h-[32px] w-[40px] bg-gray-200 rounded"
          />
        ))}
      </div>
    </div>
  )
}