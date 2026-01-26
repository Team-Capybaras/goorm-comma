'use client'

import ParkFilter from '@/app/(view)/(main)/_component/ParkFilter'
import ParkCard from '@/app/(view)/(main)/_component/ParkCard'
import { useEffect, useRef, useState } from 'react'
import { ParkInfo, ParkListWithPage } from '@/shared/types/park-types'
import { InfiniteData, useInfiniteQuery } from '@tanstack/react-query'
import { fetchParks } from '@/shared/libs/park-list-api'
import ParkListCardSkeleton from '@/app/(view)/(main)/_status/ParkListCardSkeleton'
import ErrorComponent from '@/components/ui/ErrorComponent'

type ParksQueryKey = ['parks', string[], string, { lat: number; lng: number }]

interface ParkLocationProps {
  location: {
    lat: number
    lng: number
  }
}

export default function ParkListContent({ location }: ParkLocationProps) {
  const [activeOptions, setActiveOptions] = useState<string[]>([])
  const [activeSort, setActiveSort] = useState<string>('BY_DISTANCE')
  const loadMoreRef = useRef<HTMLDivElement | null>(null)
  const SKELETON_COUNT = 3

  // react-qeury fetch
  const { data, fetchNextPage, hasNextPage, isFetching, isFetchingNextPage, isError, error } =
    useInfiniteQuery<
      ParkListWithPage,
      Error,
      InfiniteData<ParkListWithPage>,
      ParksQueryKey,
      string | null
    >({
      queryKey: ['parks', activeOptions, activeSort, { lat: location.lat, lng: location.lng }],
      enabled: !!location?.lat && !!location?.lng,
      queryFn: ({ pageParam }) =>
        fetchParks({
          pageParam,
          activeOptions,
          activeSort,
          location: location!,
        }),
      getNextPageParam: (lastPage) => (lastPage.hasNext ? lastPage.nextCursor : undefined),
      initialPageParam: null,
    })

  const totalCount: number = data?.pages?.[0]?.totalCount ?? 0
  const count: number = data?.pages?.[0]?.count ?? 0

  const parks: ParkInfo[] = data?.pages.flatMap((page) => page.parks) ?? []

  console.log(data)

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

  if (isError) {
    return <ErrorComponent className={'bg-white'} />
  }

  return (
    <div className="mt-[10px]">
      {/* filter */}
      <ParkFilter
        parks={parks}
        activeSort={activeSort}
        setActiveSort={setActiveSort}
        activeOptions={activeOptions}
        setActiveOptions={setActiveOptions}
      />

      {/* 초기 로딩 Skeleton */}
      {isFetching && parks.length === 0 && (
        <div className="px s-5 mt s-5 flex flex-col gap-8">
          {Array.from({ length: SKELETON_COUNT }).map((_, i) => (
            <ParkListCardSkeleton key={i} />
          ))}
        </div>
      )}

      {/* list */}
      {parks.length > 0 ? (
        <div className="px s-5">
          <p className="mt s-4">
            <span className="text-body-2-sb">
              총 {activeOptions.length !== 0 ? count : totalCount}개
            </span>
            <span className="text-body-2-m text-sub-bright">의 공원</span>
          </p>

          <div className="flex flex-col gap-8 mt s-5 mb-5">
            {parks.map((park, idx) => (
              <ParkCard key={`${park.areaName}-${idx}`} data={park} />
            ))}
          </div>

          {/* 다음 페이지(무한스크롤)이 있을 때 */}
          {hasNextPage && <div ref={loadMoreRef} className="h-10" />}

          {/* 다음 페이지(무한스크롤)을 적용 중일 때 */}
          {isFetchingNextPage && <ParkListCardSkeleton />}
        </div>
      ) : (
        /* list가 비어있을 때 */
        !isFetching && (
          <div className="flex flex-col justify-center items-center p-5 h-70">
            <img src="/images/icons/caution.svg" width={24} height={24} alt="주의" />
            <p className="text-sub text-body-2-m mt s-3">조건에 맞는 공원이 없어요.</p>
            <p className="text-sub text-body-2-m">필터를 다시 설정해보세요.</p>
          </div>
        )
      )}
    </div>
  )
}
