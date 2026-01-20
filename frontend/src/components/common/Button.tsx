import React, { ReactNode } from 'react'
import cn from '@/shared/utils/cn'

const baseStyle =
  'inline-flex items-center border border-transparent justify-center gap-2 whitespace-nowrap rounded-full text-body-2-m cursor-pointer disabled:pointer-events-none disabled:opacity-50'

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?:
    | 'default' // 기본
    | 'active' // 활성화
    | 'primary' // 브랜드 버튼
    | 'destructive' // 취소 등의 버튼
    | 'destructive-outline' // 삭제하기 버튼용
    | 'leftIcon' // 좌측에 아이콘이 올 때
    | 'rightIcon' // 우측에 아이콘이 올 때
  size?: 'default' | 'sm' | 'lg' | 'icon' // 버튼 사이즈 지정
  as?: React.ElementType

  widthFull?: boolean
  isLoading?: boolean
  leftIcon?: ReactNode
  rightIcon?: ReactNode
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  (
    {
      variant = 'default',
      size = 'default',
      as: Comp = 'button',
      type,
      className,
      widthFull = false,
      isLoading = false,
      leftIcon,
      rightIcon,
      children,
      disabled,
      ...props
    },
    ref
  ) => {
    const computedClassName = cn(
      // 기본 구성 값
      baseStyle,

      // 색상 설정 값
      variant === 'default' &&
        'border-bright',
      variant === 'active' &&
        'bg-positive border-1-line-positive text-positive',
      variant === 'primary' &&
        'bg-primary text-white',
      variant === 'destructive-outline' &&
        'border border-destructive bg-background text-destructive hover:text-black hover:bg-destructive/10 cursor-pointer',

      // 사이즈 설정 값
      size === 'default' && 'px s-4 py-2 ',
      size === 'sm' && 'h-9 px-3',
      size === 'lg' && 'h-11 px-8 text-base', // lg 사이즈만 폰트 크기 확대
      size === 'icon' && 'h-10 w-10',

      // 버튼 길이 설정 값
      widthFull && 'w-full',

      // 커스텀 설정
      className
    )

    const Component = Comp as any

    return (
      <Component
        ref={ref}
        type={type ?? (Comp === 'button' ? 'button' : undefined)}
        className={computedClassName}
        disabled={disabled || isLoading}
        {...props}
      >
        <>
          {leftIcon && <span className="shrink-0">{leftIcon}</span>}
          {children}
          {rightIcon && <span className="shrink-0">{rightIcon}</span>}
        </>
      </Component>
    )
  }
)

Button.displayName = 'Button'

export default Button
