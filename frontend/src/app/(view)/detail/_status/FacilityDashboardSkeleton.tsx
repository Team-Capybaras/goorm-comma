export default function FacilityDashboardSkeleton() {
  return (
    <div className="mt s-6 mb-6 animate-pulse">
      {/* title */}
      <h3 className="text-body-1-sb">주변 대중교통 및 편의시설</h3>

      {/* map area */}
      <div className="w-full h-[240px] bg-gray-200 rounded-xl mb s-4" />

      {/* info list */}
      <div className="space-y-3">
        {[0, 1, 2].map((i) => (
          <div
            key={i}
            className="flex items-center gap-3"
          >
            <div className="w-5 h-5 bg-gray-200 rounded" />
            <div className="h-[14px] w-[200px] bg-gray-200 rounded" />
          </div>
        ))}
      </div>
    </div>
  )
}
