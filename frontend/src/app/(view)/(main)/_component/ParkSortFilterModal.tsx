import { ParkInfo } from '@/shared/types/park-types'
import Image from 'next/image'

interface ParkSortFilterModalProps {
  data : string
  setShow: React.Dispatch<React.SetStateAction<boolean>>
  setActiveSort: React.Dispatch<React.SetStateAction<string>>
}

export default function ParkSortFilterModal({
  data,
  setShow,
  setActiveSort
  }: ParkSortFilterModalProps) {
  const sortMap = {
    distance: data === 'BY_DISTANCE',
    congestion: data === 'LOW_CONGESTION',
  } as const

  const { distance: isDistance, congestion: isCongestion } = sortMap

  return (
    <>
      <div className="flex justify-between ">
        <p className="text-subtitle-2-sb">정렬 옵션</p>
        <Image
          src="/images/icons/close.svg"
          className="cursor-pointer"
          width={24}
          height={24}
          onClick={() => setShow(false)}
          alt="닫기"
        />
      </div>
      <ul className="mt s-6">
        <li className="py s-4">
          <button className="w-full flex justify-between cursor-pointer" onClick={() => setActiveSort('BY_DISTANCE')}>
            <p className={`text-body-1-m ${isDistance && 'text-primary'}`}>가까운 순</p>
            {isDistance &&
              <Image 
                src="/images/icons/check.svg"
                width={24}
                height={24}
                alt="선택"
              />
            }
          </button>

        </li>
        <li className="py s-4">
          <button className="w-full flex justify-between cursor-pointer" onClick={() => setActiveSort('LOW_CONGESTION')}>
            <p className={`text-body-1-m ${isCongestion && 'text-primary'}`}>한적한 순</p>
            {isCongestion && 
              <Image
                src="/images/icons/check.svg"
                width={24}
                height={24}
                alt="선택"
              />
            }
          </button>
        </li>
      </ul>
    </>
  )
}