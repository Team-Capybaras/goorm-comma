'use client'

import Image from "next/image";
import Input from "@/components/common/Input";
import SearchModal from "@/app/(view)/(main)/_component/SearchModal";
import {useEffect, useState} from "react";
import {ParkList} from "@/shared/types/park-types";

interface SearchClientProps {
  data: ParkList[]
}

export default function SearchClient ({data}: SearchClientProps) {
  const [keyword, setKeyword] = useState<string>('')
  const [onfocus, setOnfocus] = useState<boolean>(false)

  useEffect(() => {
    if (typeof document === 'undefined') return;

    document.body.style.overflow = onfocus ? 'hidden' : '';
  }, [onfocus])

  return (
    <>
      {/* 검색 Input */}
      <div className="relative w-full">
        <img
          src="/images/icons/search.svg"
          alt="검색"
          width={26}
          height={26}
          className="absolute left-6 top-1/2 -translate-y-1/2 text-sub"
        />
        <Input
          onChange={(e) => {
            setKeyword(e.target.value)
            setOnfocus(e.target.value !== "")
          }}
          value={keyword}
          placeholder="공원 이름이나 지역을 검색해요"
          className="pl-15 h-12 rounded-full border-default"
        />
        {onfocus &&
          <button
            className="absolute right-5 top-1/2 -translate-y-1/2"
            onClick={() => {
              setKeyword("")
              setOnfocus(false)
            }}
          >
            <img src="/images/icons/closecircle.svg" alt="삭제"/>
          </button>
        }
      </div>

      {/* 검색 모달 */}
      {onfocus && <SearchModal keyword={keyword} data={data} />}
    </>
  )
}