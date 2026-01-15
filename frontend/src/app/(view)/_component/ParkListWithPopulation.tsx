import { Card } from '@/components/common/Card'
import Image from 'next/image'
import ParkThumbnail from '@/components/common/ParkThumbnail'

export default function ParkListWithPopulation() {
  return (
    <div className="flex bg-bright justify-center mb-3">
      <div className="w-full max-w-2xl flex flex-col gap-4 p-5">
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
        <div className="flex gap-2 overflow-hidden">
          <Card className="h-10 px-[16px] py-2 border-default rounded-full">
            <Image
              src="/images/icons/slider.svg"
              alt="필터"
              width={22}
              height={22}
            />
          </Card>
          <Card className="inline-flex h-10 px-[14px] py-1.5 border-default rounded-full">
            가까운 순
            <Image
              src="/images/icons/arrow/down.svg"
              alt="펼치기"
              width={20}
              height={20}
              className="ml-2"
            />
          </Card>
          <Card className="w-fit h-10 px-[12px] py-1.5 border-default rounded-full">
            태그1
          </Card>
          <Card className="w-fit h-10 px-[12px] py-1.5 border-default rounded-full">
            태그2
          </Card>
        </div>

        <div> {/* 공원 수 및 공원 정보 추후 map으로 생성 */}
          <p className="pb-5">
            <span className="text-body-2-sb">총 xx개</span>
            <span className="text-body-2-m text-sub-bright">의 공원</span>
          </p>
          <div className="flex gap-3">
            <Card className="w-[345px] h-[325px] border-default rounded-8 bg-gray-500 overflow-hidden">
              <ParkThumbnail  
                height={325}
                dotBottom={12}
                data={[
                  '/test.jpg',
                  '/test.jpg',
                  '/test.jpg',
                  '/test.jpg',
                ]} 
              />
              
            </Card>
          </div>
          <div className="flex flex-col gap-2">
            <div className="mt-3">
              <p><span className="text-body-1-sb mr-3">공원 이름</span><span className="text-body-2-m">거리</span></p>
              <p><span className="text-body-2-b mr-3">붐비는 정도</span><span className="text-caption-1-m">예상 날씨</span></p>
            </div>
            <div className="flex gap-3">
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