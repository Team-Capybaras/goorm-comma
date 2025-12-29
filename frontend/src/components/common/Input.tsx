import { InputHTMLAttributes, forwardRef } from 'react'
import cn from '@/shared/utils/cn'

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  error?: boolean
}

const Input = forwardRef<HTMLInputElement, InputProps>(({ className, error, ...props }, ref) => {
  return (
    <input
      ref={ref}
      className={cn(
        'flex w-full px-3 py-2 text-base',
        'border border-gray-300 bg-transparent',
        'outline-none focus:border-black',
        error && 'border-red-500',
        className
      )}
      {...props}
    />
  )
})

Input.displayName = 'Input'

export default Input
