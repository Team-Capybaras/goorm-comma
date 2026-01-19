'use client'

import Image from "next/image";
import Input from "@/components/common/Input";
import SearchModal from "@/app/(view)/_component/SearchModal";
import {useState} from "react";
import {ParkList} from "@/shared/types/park-types";

interface SearchClientProps {
  data: ParkList[]
}

export default function SearchClient ({data}: SearchClientProps) {
  const [keyword, setKeyword] = useState<string>('')
  const [onfocus, setOnfocus] = useState<boolean>(false)

  return (
    <>
      {/* 검색 Input */}
      <div className="relative w-full">
        <Image
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
          placeholder="공원을 검색해보세요"
          className="pl-15 h-12 rounded-full border-default"
        />
      </div>

      {/* 검색 모달 */}
      {onfocus && <SearchModal keyword={keyword} data={data} />}
    </>
  )
}