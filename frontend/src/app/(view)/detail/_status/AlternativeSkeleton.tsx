import AlternativeParkClient from '@/app/(view)/detail/_components/AlternativeParkClient'

export default function AlternativeSkeleton() {
  return (
    <div className="mt s-5 pb-8 animate-pulse">
      {/* header */}
      <div className="flex gap s-1 relative">
        <h3 className="pl s-6 text-subtitle-2-sb">지금 갈만한 공원</h3>
      </div>

      {/* carousel */}
      <div className="mt s-4 flex gap-3">
          {[0, 1, 2].map((i) => (
            <div
              key={i}
              className={`flex-[0_0_40%] ${i === 0 ? 'ml s-6' : ''}`}
            >
              {/* image */}
              <div className="w-full h-[220px] bg-gray-200 rounded-6" />

              {/* title */}
              <div className="h-[16px] w-[100px] bg-gray-200 rounded mt s-3" />

              {/* subtitle */}
              <div className="h-[12px] w-[60px] bg-gray-200 rounded mt s-2" />
            </div>
          ))}
      </div>
    </div>
  )
}
