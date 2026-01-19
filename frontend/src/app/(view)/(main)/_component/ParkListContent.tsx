'use client'

import ParkFilter from "@/app/(view)/(main)/_component/ParkFilter";
import ParkCard from "@/app/(view)/(main)/_component/ParkCard";
import {useEffect, useRef, useState} from "react";
import {ParkInfo, ParkListWithPage} from "@/shared/types/park-types";
import {InfiniteData, useInfiniteQuery} from "@tanstack/react-query";
import {fetchParks} from "@/shared/libs/park-list-api";

type ParksQueryKey = [
  'parks',
  string[],
  { lat: number; lng: number }
]

interface ParkLocationProps {
  location: {
    lat: number
    lng: number
  }
}

export default function ParkListContent({location} : ParkLocationProps) {
  const [activeOptions, setActiveOptions] = useState<string[]>([])
  const loadMoreRef = useRef<HTMLDivElement | null>(null)

  // react-qeury fetch
  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetching,
    isFetchingNextPage,
  } = useInfiniteQuery<
    ParkListWithPage,
    Error,
    InfiniteData<ParkListWithPage>,
    ParksQueryKey,
    string | null
  >({
    queryKey: ['parks', activeOptions, {lat: location.lat, lng: location.lng}],
    enabled: !!location?.lat && !!location?.lng,
    queryFn: ({ pageParam }) =>
      fetchParks({
        pageParam,
        activeOptions,
        location: location!,
      }),
    getNextPageParam: (lastPage) =>
      lastPage.hasNext ? lastPage.nextCursor : undefined,
    initialPageParam: null,
  })

  const parks: ParkInfo[] =
    data?.pages.flatMap((page) => page.parks) ?? []

  // 무한스크롤 observer
  useEffect(() => {
    if (!loadMoreRef.current || !hasNextPage) return

    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          fetchNextPage()
        }
      },
      { threshold: 0 }
    )

    observer.observe(loadMoreRef.current)
    return () => observer.disconnect()
  }, [fetchNextPage, hasNextPage])

  return (
    <>
      {/* filter */}
      <ParkFilter activeOptions={activeOptions} setActiveOptions={setActiveOptions}/>
      {/* list */}
      {parks.length > 0 ? (
        <>
          <p className="pb-5">
            <span className="text-body-2-sb">총 {parks.length}개</span>
            <span className="text-body-2-m text-sub-bright">의 공원</span>
          </p>

          {parks.map((park, idx) => (
            <ParkCard key={`${park.areaName}-${idx}`} data={park} />
          ))}

          {hasNextPage && (
            <div ref={loadMoreRef} className="h-10" />
          )}

          {isFetchingNextPage && (
            <p className="text-center text-caption-1-m text-sub">
              불러오는 중...
            </p>
          )}
        </>
      ) : (
        !isFetching && (
          <div className="flex justify-center items-center p-5 h-70">
            <p>조건에 맞는 공원이 없어요. 필터를 다시 설정해보세요</p>
          </div>
        )
      )}
    </>
  )
}