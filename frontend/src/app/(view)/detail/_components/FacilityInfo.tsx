import Image from "next/image";

export default function FacilityInfo() {
  return (
    <div className="grid grid-cols-3 mt-3">
      <div className="flex items-center justify-start gap s-2">
        <img src="/images/icons/facility/parking.svg" width={16} height={16} alt={'주차장'} />
        <p className="font-xs">주차공간</p>
      </div>
      <div className="flex items-center justify-start gap s-2">
        <Image
          src="/images/icons/facility/electric.svg"
          width={16}
          height={16}
          alt={'전기차 충전소'}
        />
        <p className="font-xs">전기차 충전소</p>
      </div>
      <div className="flex items-center justify-start gap s-2">
        <img src="/images/icons/facility/bicycle.svg" width={16} height={16} alt={'따릉이'} />
        <p className="font-xs">따릉이</p>
      </div>
      <div className="flex items-center justify-start gap s-2">
        <img src="/images/icons/facility/subway.svg" width={16} height={16} alt={'지하철'} />
        <p className="font-xs">지하철</p>
      </div>
      <div className="flex items-center justify-start gap s-2">
        <img src="/images/icons/facility/bus.svg" width={16} height={16} alt={'버스'} />
        <p className="font-xs">버스</p>
      </div>
    </div>
  )
}