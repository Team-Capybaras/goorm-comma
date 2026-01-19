'use client'

import Image from "next/image";
import ParkDirectionModal from "@/app/(view)/detail/_components/ParkDirectionModal";
import {ParkInfo} from "@/shared/types/park-types";
import {useState} from "react";

interface ParkInfoClientProps {
  data : ParkInfo
}

export default function ParkInfoClient ({data}:ParkInfoClientProps) {
  const [show, setShow] = useState<boolean>(false)

  return (
    <>
      <div className="flex items-center cursor-pointer" onClick={() => setShow(true)}>
        <p className="text-caption-1-m text-sub-bright">길찾기</p>
        <Image src={"/images/icons/arrow/right-gray.svg"} width={16} height={16} alt={"바로가기"}/>
      </div>

      {show && <ParkDirectionModal data={data} setShow={setShow}/>}
    </>
  )
}