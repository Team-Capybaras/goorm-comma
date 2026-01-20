export default function ParkInfoDashboardSkeleton() {
  return (
    <div className="px s-5 animate-pulse">
      {/* 공원 이름 및 거리 */}
      <div className="flex items-center mb s-3">
        <div className="h-[24px] w-[160px] bg-gray-200 rounded mr s-2-sub" />
        <div className="h-[16px] w-[48px] bg-gray-200 rounded" />
      </div>

      {/* 혼잡도 요약, 주소, 버튼 */}
      <div className="flex items-center">
        <div className="h-[16px] w-[48px] bg-gray-200 rounded" />
        <div className="w-0.75 h-0.75 rounded-full mx-2 bg-gray-200" />
        <div className="h-[14px] w-[180px] bg-gray-200 rounded mr s-3" />
        <div className="h-[20px] w-[56px] bg-gray-200 rounded" />
      </div>

      {/* 공원 둘러보기 카드 */}
      <div className="border-1-line-default rounded-2xl p s-4 mt-4">
        <div className="h-[16px] w-[120px] bg-gray-200 rounded mb s-3" />

        <div className="flex flex-col gap-3">
          {[0, 1, 2].map((i) => (
            <div key={i} className="flex items-center gap-3">
              <div className="w-3.5 h-3.5 bg-gray-200 rounded" />
              <div className="h-[14px] w-[200px] bg-gray-200 rounded" />
            </div>
          ))}
        </div>
      </div>

      {/* 태그 */}
      <div className="flex gap-2 mt s-4">
        <div className="h-[24px] w-[72px] bg-gray-200 rounded-full" />
        <div className="h-[24px] w-[56px] bg-gray-200 rounded-full" />
        <div className="h-[24px] w-[64px] bg-gray-200 rounded-full" />
      </div>
    </div>
  )
}
