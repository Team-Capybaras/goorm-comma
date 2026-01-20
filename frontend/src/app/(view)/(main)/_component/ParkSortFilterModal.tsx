import {ParkInfo} from "@/shared/types/park-types";

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
    distance: data === 'distance',
    congestion: data === 'congestion',
  } as const

  const { distance: isDistance, congestion: isCongestion } = sortMap

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
        <ul className="mt s-6">
          <li className="py s-4">
            <button className="w-full flex justify-between cursor-pointer" onClick={() => setActiveSort('distance')}>
              <p className={`text-body-1-m ${isDistance && 'text-primary'}`}>가까운 순</p>
              {isDistance && <img src="/images/icons/check.svg" alt="선택"/>}
            </button>

          </li>
          <li className="py s-4">
            <button className="w-full flex justify-between cursor-pointer" onClick={() => setActiveSort('congestion')}>
              <p className={`text-body-1-m ${isCongestion && 'text-primary'}`}>한적한 순</p>
              {isCongestion && <img src="/images/icons/check.svg" alt="선택"/>}
            </button>
          </li>
        </ul>
      </div>

      {/* overlay */}
      <div className="fixed w-full h-full top-0 left-0 inset-0 bg-black/40 z-98"
           onClick={() => setShow(false)}
      ></div>
    </>
  )
}