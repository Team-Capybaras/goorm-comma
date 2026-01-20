import {ParkInfo} from "@/shared/types/park-types";
import Button from "@/components/common/Button";
import {useEffect, useState} from "react";
import useToast from "@/components/common/ToastContainer";

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
  const { showToast, ToastContainer } = useToast()
  const [selected, setSelected] = useState<string[]>(data)

  const toggleOption = (option: string) => {
    setSelected(prev =>
      prev.includes(option) ? prev.filter(o => o !== option) : [...prev, option]
    )
  }

  // 공원이 없는 경우 Toast
  useEffect(() => {
    if (parks.length === 0) {
      showToast("조건에 맞는 공원이 없어요. 필터를 다시 설정해보세요.", 'default', 2000)
    }
  }, [parks, showToast])

  const applyFilter = () => {
    setActiveOptions(selected)
  }

  const resetFilter = () => {
    setSelected([])
  }

  return (
    <>
      <div className="fixed bottom-0 left-0 z-99 w-full bg-bright pt-8 rounded-t-xl px s-5 py-12">
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
      </div>

      {ToastContainer}

      {/* overlay */}
      <div className="fixed w-full h-full top-0 left-0 inset-0 bg-black/40 z-98"
           onClick={() => setShow(false)}
      ></div>
    </>
  )
}