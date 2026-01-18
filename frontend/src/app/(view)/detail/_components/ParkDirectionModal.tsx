import {ParkInfo} from "@/shared/types/park-types";

interface ParkInfoModalProps {
  data : ParkInfo
  setShow: React.Dispatch<React.SetStateAction<boolean>>
}

export default function ParkDirectionModal ({data, setShow}:ParkInfoModalProps) {
  return (
    <>
      <div className="fixed bottom-0 left-0 z-99 w-full bg-bright pt-8 rounded-t-xl px s-5 py-12">
        <div className="flex justify-between ">
          <p className="text-subtitle-2-sb">길찾기</p>
          <img src="/images/icons/close.svg"
               className="w-[24px] h-[24px] cursor-pointer"
               onClick={() => setShow(false)}
               alt="닫기"/>
        </div>
        <ul className="mt s-6">
          <li className="py s-4">
            <a href={`https://map.naver.com/index.nhn?menu=route&etext=${data.areaName}&elat=${data.latitude}&elng=${data.longitude}&pathType=0&showMap=true&menu=route`}
               className="flex block items-center gap s-4"
               target={"_blank"}>
              <img src="/images/icons/naver.svg" className="w-8 h-8" alt="네이버"/>
              <p className="text-body-2-r">네이버 지도</p>
            </a>
          </li>
          <li className="py s-4">
            <a href={`https://map.kakao.com/link/to/${data?.areaName},${data?.latitude},${data.longitude}`}
               className="flex block items-center gap s-4"
               target={"_blank"}>
              <img src="/images/icons/kakao.svg" className="w-8 h-8" alt="카카오"/>
              <p className="text-body-2-r">카카오맵</p>
            </a>
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