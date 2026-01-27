import { ParkInfo } from '@/shared/types/park-types'
import Button from '@/components/common/Button'
import { useState } from 'react'

interface ParkFilterModalProps {
  parks: ParkInfo[]
  data : string[]
  options: string[]
  setShow: React.Dispatch<React.SetStateAction<boolean>>
  setActiveOptions: React.Dispatch<React.SetStateAction<string[]>>
}

export default function ParkFilterModal({
    parks,
    data,
    options,
    setShow,
    setActiveOptions
  }: ParkFilterModalProps) {
  const [selected, setSelected] = useState<string[]>(data)

  const toggleOption = (option: string) => {
    setSelected(prev =>
      prev.includes(option) ? prev.filter(o => o !== option) : [...prev, option]
    )
  }

  // 필터 적용 후 모달 닫힘
  const applyFilter = () => {
    setActiveOptions(selected)
    setShow(false)
  }

  // 필터 초기화
  const resetFilter = () => {
    setSelected([])
  }

  return (
    <>
      <div className="flex justify-between ">
        <p className="text-subtitle-2-sb">정렬 옵션</p>
        <img src="/images/icons/close.svg"
             className="w-[24px] h-[24px] cursor-pointer"
             onClick={() => setShow(false)}
             alt="닫기"/>
      </div>
      <p className="text-body-2-sb mt s-6">공원 특징</p>
      <ul className="mt s-3 flex flex-wrap gap-1">
        {options.map((option) => (
          <li key={option}>
            <Button variant={selected.includes(option) ? 'active' : 'default'}
                    onClick={() => toggleOption(option)}>
              <span className="text-body-2-m">{option}</span>
            </Button>
          </li>
        ))}
      </ul>
      <div className="flex gap-2 mt s-8">
        <Button
          className="w-full rounded-[10px]"
          onClick={() => resetFilter()}
        >
          <span className="text-body-1-m">초기화</span>
        </Button>
        <Button variant={"primary"} onClick={applyFilter} className="w-full rounded-[10px]">
          <span className="text-body-1-m">적용</span>
        </Button>
      </div>
    </>
  )
}