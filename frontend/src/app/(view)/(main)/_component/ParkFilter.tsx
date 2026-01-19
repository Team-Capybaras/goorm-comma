import Button from "@/components/common/Button";
import EmblaCarousel from "@/components/common/EmblaCarousel";
import {EmblaOptionsType} from "embla-carousel";

const OPTIONS: EmblaOptionsType = { dragFree: true }

interface ParkFilterProps {
  activeOptions: string[]
  setActiveOptions: React.Dispatch<React.SetStateAction<string[]>>
}

export default function ParkFilter ({activeOptions, setActiveOptions}:ParkFilterProps) {
  const filterOptions: string[] = ['한강뷰', '산/숲', '평지', '수변/폭포', '산책', '피크닉', '역사/문화', '랜드마크']

  const toggleOption = (option: string) => {
    setActiveOptions(prev =>
      prev.includes(option)
        ? prev.filter(o => o !== option)
        : [...prev, option]
    )
  }

  return (
    <div className="flex gap s-2">
      <Button variant={activeOptions.length > 0 ? "active" : "default"} className="shrink-0">
        <img src="/images/icons/slider.svg" width={22} alt={"아이콘"}/>
      </Button>

      <Button
        className="shrink-0"
        rightIcon={<img src="/images/icons/arrow/down.svg" width={16} alt={"아이콘"}/>}
      >
        <p className="text-body-2-m text-center pl-1">가까운 순</p>
      </Button>
      <div className="flex-1 min-w-0">
        <EmblaCarousel
          options={OPTIONS}
          containerClassName="gap s-2"
          viewportClassName="flex-1"
        >
          {filterOptions.map(option => (
            <div key={option} className="shrink-0">
              <Button variant={activeOptions.includes(option) ? 'active' : 'default'}
                      onClick={() => toggleOption(option)}>
                <span className="text-body-2-m">{option}</span>
              </Button>
            </div>
          ))}
        </EmblaCarousel>
      </div>
    </div>
  )
}