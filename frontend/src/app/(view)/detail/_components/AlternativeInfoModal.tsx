import Image from "next/image";

interface AlternativeInfoModalProps {
  setShow: React.Dispatch<React.SetStateAction<boolean>>
}

export default function AlternativeInfoModal ({setShow}: AlternativeInfoModalProps) {
  return (
    <div className="absolute bg-white z-99 left-0 top-[30px] w-[250px] ml s-6 border-bright px-4 py-[10px] rounded-4">
      <div className="flex justify-between items-center">
        <h4 className="text-body-2-sb">추천 기준</h4>
        <button onClick={() => setShow(false)}>
          <img src={'/images/icons/close.svg'} width={16} height={16} alt={'닫기'}/>
        </button>
      </div>
      <div className="mt-[10px] text-body-2-r w-[200px]">
        <p>
          지금 조회한 공원 주변 공원을 비교했어요. 더 한적한 곳부터 추천해요.
        </p>
      </div>
    </div>
  )
}