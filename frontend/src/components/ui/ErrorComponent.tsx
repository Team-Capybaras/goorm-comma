import cn from "@/shared/utils/cn";

export default function ErrorComponent ({className}: {className?: string}) {
  return (
    <div className={cn('bg-gray-100 rounded-xl',className)}>
      <div className="flex flex-col justify-center items-center px-4 py-6 h-70">
        <img src="/images/icons/caution.svg" width={24} height={24} alt="주의"/>
        <p className="text-sub text-body-2-m mt s-3">정보를 불러오지 못했어요.</p>
        <p className="text-sub text-body-2-m">잠시 후 다시 시도해 주세요.</p>
      </div>
    </div>
  )
}