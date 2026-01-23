import Image from "next/image";
import ErrorComponent from "@/components/ui/ErrorComponent";

export default function ParkInfoDashboardError () {
  return (
    <div className="px s-5">
      <div className="flex items-center mb s-2">
        <p className="text-title-2-sb mr s-2-sub">공원 이름 정보 없음</p>
        <p className="text-sub">거리 정보 없음</p>
      </div>

      {/* 혼잡도 요약, 주소, 길찾기 */}
      <div className="flex items-center">
        <p className={`font-b font-sm text-gray-400`}>혼잡도 정보 없음</p>
        <div className="w-0.75 h-0.75 rounded-full mx-2 bg-deep"></div>
        <div className="flex items-center cursor-pointer">
          <p className="text-caption-1-m text-sub-bright">길찾기</p>
          <Image src={"/images/icons/arrow/right-gray.svg"} width={16} height={16} alt={"바로가기"}/>
        </div>
      </div>

      {/* 공원 둘러보기 */}
      <ErrorComponent className={"mt s-4"}/>
    </div>
  )
}