import Image from "next/image";

interface AlternativeInfoModalProps {
  setShow: React.Dispatch<React.SetStateAction<boolean>>
}

export default function AlternativeInfoModal ({setShow}: AlternativeInfoModalProps) {
  return (
    <div className="absolute bg-white z-99 left-0 top-[30px] w-[300px] ml s-6 border-bright px-4 py-[10px] rounded-4">
      <div className="flex justify-between items-center">
        <h4 className="text-body-2-sb">지금 갈만한 공원</h4>
        <button onClick={() => setShow(false)}>
          <Image src={'/images/icons/close.svg'} width={16} height={16} alt={'닫기'}/>
        </button>
      </div>
      <div className="mt-[10px] text-body-2-r w-[200px]">
        <p>
          현재 위치 기준으로 가까운 공원부터, 그중 더 한적한 곳을 먼저 추천해요.
        </p>
      </div>
    </div>
  )
}