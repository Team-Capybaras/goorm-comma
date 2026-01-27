'use client'

import AlternativeInfoModal from "@/app/(view)/detail/_components/AlternativeInfoModal";
import {useState} from "react";

export default function AlternativeParkClient() {
  const [show, setShow] = useState(false)

  return (
    <>
      <button onClick={() => setShow(!show)}>
        <img src={"/images/icons/info.svg"} width={18} height={18} alt={"안내"}/>
      </button>
      {show && <AlternativeInfoModal setShow={setShow} />}
    </>
  )
}