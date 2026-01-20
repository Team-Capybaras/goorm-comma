import {Card} from "@/components/common/Card";

export default function ParkListCardSkeleton() {
  return (
    <Card className="border-0 animate-pulse">
      {/* Thumbnail */}
      <div className="w-full h-[325px] rounded-8 bg-gray-200" />

      {/* title + distance */}
      <div className="flex items-end mt s-4 gap-2">
        <div className="h-5 w-32 rounded bg-gray-200" />
        <div className="h-4 w-12 rounded bg-gray-200" />
      </div>

      {/* congestion + weather */}
      <div className="flex items-center mt s-3 gap-2">
        <div className="h-4 w-14 rounded bg-gray-200" />
        <div className="w-1 h-1 rounded-full bg-gray-300" />
        <div className="h-4 w-4 rounded-full bg-gray-200" />
        <div className="h-4 w-24 rounded bg-gray-200" />
      </div>

      {/* tags */}
      <div className="flex gap-2 mt s-3">
        <div className="h-6 w-20 rounded-full bg-gray-200" />
        <div className="h-6 w-16 rounded-full bg-gray-200" />
        <div className="h-6 w-14 rounded-full bg-gray-200" />
      </div>
    </Card>
  )
}
