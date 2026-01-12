import { ComponentPropsWithRef, ReactNode, MouseEvent } from 'react'
import cn from '@/shared/utils/cn'
import { CardCloseButton } from '@/components/common/CardCloseButton'

// Card 정의
interface CardProps extends ComponentPropsWithRef<'div'> {
  children?: ReactNode
}

// className이랑 props 전달받아서 사용
export function Card({ className, children, ...props }: CardProps) {
  return (
    <div
      className={cn('relative rounded-xl border border-border bg-white text-black', className)}
      {...props}
    >
      {children}
    </div>
  )
}

// CardHeader 정의
interface CardHeaderProps extends ComponentPropsWithRef<'div'> {
  children?: ReactNode // 내용
  left?: ReactNode // 좌측 설정
  right?: ReactNode // 우측 설정
  closable?: boolean // 닫기 가능 여부
  onClose?: (event: MouseEvent<HTMLButtonElement>) => void // 닫기 함수
}

export function CardHeader({
  className,
  children,
  left,
  right,
  closable,
  onClose,
  ...props
}: CardHeaderProps) {
  return (
    <div className={cn('flex items-center justify-between p-6 text-black', className)} {...props}>
      {/* 좌측 */}
      {left && <div className="mr-2">{left}</div>}
      {/* 일반 설정 */}
      <div className="flex-1 min-w-0 whitespace-pre-line wrap-break-word hyphens-auto">
        {children}
      </div>
      {/* 우측 및 닫기 버튼 */}
      {(right || closable) && (
        <div className="ml-4 flex items-center gap-2">
          {right}
          {closable && <CardCloseButton onClose={onClose} />}
        </div>
      )}
    </div>
  )
}

// CardContent 정의
interface CardContentProps extends ComponentPropsWithRef<'div'> {
  children?: ReactNode // 내용
  center?: ReactNode // 중앙 설정
}

export function CardContent({ className, children, center, ...props }: CardContentProps) {
  return (
    <div className={cn('p-6 text-black', className)} {...props}>
      {center && <div className="flex items-center justify-center text-black">{center}</div>}
      {children}
    </div>
  )
}

// CardFooter 정의
interface CardFooterProps extends ComponentPropsWithRef<'div'> {
  children?: ReactNode // 내용
  center?: ReactNode // 중앙 설정
}

export function CardFooter({ className, children, center, ...props }: CardFooterProps) {
  return (
    <div className={cn('p-6 text-black', className)} {...props}>
      {center && <div className="flex justify-center text-black">{center}</div>}
      {children}
    </div>
  )
}
