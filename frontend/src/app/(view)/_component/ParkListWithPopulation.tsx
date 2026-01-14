import { Card } from '@/components/common/Card'
import Image from 'next/image'
import ParkThumbnail from '@/components/common/ParkThumbnail'

export default function ParkListWithPopulation() {
  return (
    <div className="flex bg-bright justify-center mb-3">
      <div className="w-full max-w-2xl flex flex-col gap-lg p-xl">
      {/* 혼잡도 기반 제공 */}
        <span className="inline-flex text-caption-1-m text-sub">
          실시간 인구 기반으로 혼잡도를 알려드려요
          <Image
            src="/images/icons/info.svg"
            alt="정보"
            width={16}
            height={16}
            className="ml-2"
          />
        </span>
        <div className="flex gap-md">
          <Card className=" px-[14px] py-3 border-default rounded-full">
            <Image
              src="/images/icons/slider.svg"
              alt="필터"
              width={22}
              height={22}
            />
          </Card>
          <Card className="inline-flex px-[14px] py-3 border-default rounded-full">
            가까운 순
            <Image
              src="/images/icons/arrow.svg"
              alt="펼치기"
              width={20}
              height={20}
              className="ml-2"
            />
          </Card>
          <Card className="w-fit px-[12px] py-3 border-default rounded-full">
            태그1
          </Card>
          <Card className="w-fit px-[12px] py-3 border-default rounded-full">
            태그2
          </Card>
        </div>

        <div> {/* 공원 수 및 공원 정보 추후 map으로 생성 */}
          <p>총 공원 수</p>
          <div className="flex gap-md">
            <Card className="w-[345px] h-[325px] border-default rounded-xl bg-gray-500">
              <ParkThumbnail  
                height={325}     // ✅ 이 페이지 전용
                dotBottom={12}
                data={[
                '/images/sample/park1.jpg',
                '/images/sample/park2.jpg',
                '/images/sample/park3.jpg',
                '/images/sample/park4.jpg',
                ]} 
              />
              
            </Card>
          </div>
          <div className="flex flex-col gap-sm">
            <div className="mt-3">
              <p><span className="text-body-1-sb mr-3">공원 이름</span><span className="text-body-2-m">거리</span></p>
              <p><span className="text-body-2-b mr-3">붐비는 정도</span><span className="text-caption-1-m">예상 날씨</span></p>
            </div>
            <div className="flex gap-md">
              <Card className="w-fit px-[12px] py-3 border-default rounded-full">
                태그1
              </Card>
              <Card className="w-fit px-[12px] py-3 border-default rounded-full">
                태그2
              </Card><Card className="w-fit px-[12px] py-3 border-default rounded-full">
                태그3
              </Card>
             </div>
          </div>
        </div>
      </div>
    </div>
  )
}