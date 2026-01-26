'use client'

import {useMemo} from "react";
import Link from "next/link";
import {ParkList} from "@/shared/types/park-types";

interface SearchModalProps {
  keyword: string
  data: ParkList[]
  setFocus: React.Dispatch<React.SetStateAction<boolean>>
  setKeyword: React.Dispatch<React.SetStateAction<string>>
}

export default function SearchModal ({keyword, data, setKeyword, setFocus} : SearchModalProps) {
  /* 검색 필터링 */
  const filteredDatas = useMemo(() => {
    const q = keyword.trim().toLowerCase()
    if (!q) return []

    return data.filter(item =>
      item.areaName.toLowerCase().includes(q) ||
      item.parkAddr.toLowerCase().includes(q)
    )
  }, [data, keyword])

  function highlightKeyword(text: string, keyword: string) {
    if (!keyword) return text

    const regex = new RegExp(`(${keyword})`, 'gi')
    const parts = text.split(regex)

    return parts.map((part, idx) =>
      part.toLowerCase() === keyword.toLowerCase() ? (
        <span key={idx} className="text-primary font-semibold">
        {part}
      </span>
      ) : (
        <span key={idx}>{part}</span>
      )
    )
  }

  return (
    <div className="h-full w-full max-w-2xl  bg-bright z-99 fixed top-[172px] left-[50%] translate-x-[-50%]">
      <ul className="px-5">
        {filteredDatas.length === 0 && (
          <li className="text-body-1-sb text-sub-bright">검색 결과가 없습니다.</li>
        )}

        {filteredDatas.map((data) => (
          <li key={data.areaCode} className="py-[12px]">
            <Link href={`/detail/${data.areaCode}`} onClick={() => {
              setFocus(false)
              setKeyword("")
            }} className="block w-full text-body-1-m">
              {highlightKeyword(data.areaName, keyword)}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )
}