'use client'

import Button from "@/components/common/Button";
import EmblaCarousel from "@/components/common/EmblaCarousel";
import {EmblaOptionsType} from "embla-carousel";
import ParkSortFilterModal from "@/app/(view)/(main)/_component/ParkSortFilterModal";
import {useState} from "react";
import ParkFilterModal from "@/app/(view)/(main)/_component/ParkFilterModal";
import {ParkInfo} from "@/shared/types/park-types";
import {Modal} from "@/components/ui/Modal";
import Image from 'next/image'

const OPTIONS: EmblaOptionsType = { dragFree: true }

interface ParkFilterProps {
  parks: ParkInfo[]
  activeSort: string
  setActiveSort: React.Dispatch<React.SetStateAction<string>>
  activeOptions: string[]
  setActiveOptions: React.Dispatch<React.SetStateAction<string[]>>
}

export default function ParkFilter ({
    parks,
    activeSort,
    setActiveSort,
    activeOptions,
    setActiveOptions,
  }:ParkFilterProps) {
  const filterOptions: string[] = ['한강뷰', '산/숲', '평지', '수변/폭포', '산책', '피크닉', '역사/문화', '랜드마크']
  const [showSortModal, setShowSortModal] = useState<boolean>(false)
  const [showFilterModal, setShowFilterModal] = useState<boolean>(false)

  const toggleOption = (option: string) => {
    setActiveOptions(prev =>
      prev.includes(option)
        ? prev.filter(o => o !== option)
        : [...prev, option]
    )
  }

  return (
    <div className="flex gap s-2">
      <div className="flex-1 min-w-0">
        <EmblaCarousel
          options={OPTIONS}
          containerClassName="gap s-2"
          viewportClassName="flex-1"
        >
          <Button
            variant={activeOptions.length > 0 ? "active" : "default"}
            onClick={() => setShowFilterModal(true)}
            className="shrink-0 ml s-5">
            <Image src="/images/icons/slider.svg" width={22} height={22} alt={"아이콘"}/>
          </Button>

          <Button
            className="shrink-0"
            onClick={() => setShowSortModal(true)}
            rightIcon={<Image src="/images/icons/arrow/down.svg" width={16} height={16} alt={"아이콘"}/>}
          >
            <p className="text-body-2-m text-center pl-1">
              {activeSort === 'BY_DISTANCE' ? '가까운 순' : '한적한 순'}
            </p>
          </Button>
          {filterOptions.map((option, i) => (
            <div key={option} className={`shrink-0 ${i=== filterOptions.length - 1 ? 'mr s-5' : ''}`}>
              <Button variant={activeOptions.includes(option) ? 'active' : 'default'}
                      onClick={() => toggleOption(option)}>
                <span className="text-body-2-m">{option}</span>
              </Button>
            </div>
          ))}
        </EmblaCarousel>
      </div>
      <Modal open={showFilterModal} onClose={() => setShowFilterModal(false)} size={"full"} >
        <ParkFilterModal
          parks={parks}
          data={activeOptions}
          options={filterOptions}
          setShow={setShowFilterModal}
          setActiveOptions={setActiveOptions}
        />
      </Modal>
      <Modal open={showSortModal} onClose={() => setShowSortModal(false)} size={"full"} >
        <ParkSortFilterModal
          data={activeSort}
          setShow={setShowSortModal}
          setActiveSort={setActiveSort}/>
      </Modal>
    </div>
  )
}