import {ParkInfo} from "@/shared/types/park-types";
import Button from "@/components/common/Button";

interface ParkFilterModalProps {
  data : string[]
  options: string[]
  setShow: React.Dispatch<React.SetStateAction<boolean>>
  toggleOption: (option: string) => void
}

export default function ParkFilterModal({
    data,
    options,
    setShow,
    toggleOption
  }: ParkFilterModalProps) {
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
              <Button variant={data.includes(option) ? 'active' : 'default'}
                      onClick={() => toggleOption(option)}>
                <span className="text-body-2-m">{option}</span>
              </Button>
            </li>
          ))}
        </ul>
      </div>

      {/* overlay */}
      <div className="fixed w-full h-full top-0 left-0 inset-0 bg-black/40 z-98"
           onClick={() => setShow(false)}
      ></div>
    </>
  )
}