import Input from '@/components/common/Input'
import Image from 'next/image'

export default function SearchPark() {
  return (
    <div className="flex flex-col gap-3">
      <p className="text-title-1-b">
        어디서 쉬고 싶으세요?
      </p>
      {/* 검색 컨테이너 */}
      <div className="relative w-full"> 
        <Image
          src="/images/icons/search.svg"
          alt="검색"
          width={26}
          height={26}
          className="absolute left-6 top-1/2 -translate-y-1/2 text-sub"
        />
        <Input
          placeholder="공원을 검색해보세요"
          className="pl-15 h-12 rounded-full border-default"
        />
      </div>
    </div>
  )
}
