import cn from '@/shared/utils/cn'

interface TagProps {
  children: React.ReactNode
  variant?: 'default' | 'blue'
  className?: string
}

const variantStyles = {
  default: 'bg-default text-sub-deep',
  blue: 'bg-blue-0 text-blue-500',
} as const

export default function Tag({
    children,
    variant = 'default',
    className,
  }: TagProps) {
  return (
    <span
      className={cn(
        'inline-flex items-center rounded-md px s-3 text-xs h-[24px] font-sb ',
        variantStyles[variant],
        className
      )}
    >
      {children}
    </span>
  )
}