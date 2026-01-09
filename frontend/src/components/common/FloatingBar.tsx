'use client'

import Link from 'next/link'
import Image from 'next/image'
import { usePathname } from 'next/navigation'

const navItems = [
  {
    href: '/',
    label: '홈',
    iconOn: '/images/icons/home/Vector.svg',
    iconOff: '/images/icons/home/line.svg',
  },
  {
    href: '/map',
    label: '지도',
    iconOn: '/images/icons/map/Vector.svg',
    iconOff: '/images/icons/map/line.svg',
  },
]

export default function FloatingBar() {
  const pathname = usePathname()

  return (
    <nav className="fixed bottom-8 left-1/2 -translate-x-1/2 z-50">
      <div
        className="
          flex items-center justify-between 
          w-[184px] h-[56px] 
          p-2 gap-2
          bg-white rounded-full 
          shadow-[0_0_16px_rgba(232,232,232,0.3),0_0_8px_rgba(56,57,56,0.2)]
        "
      >
        {navItems.map((item) => {
          const isActive = pathname === item.href

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`
                flex-1 h-[40px] flex items-center justify-center gap-1.5 rounded-full transition-all duration-200
                ${
                  isActive
                    ? 'bg-positive-pale text-primary font-semibold'
                    : 'bg-transparent text-sub font-medium hover:bg-gray-50'
                }
              `}
            >
              <div className="relative w-5 h-5">
                <Image
                  src={isActive ? item.iconOn : item.iconOff}
                  alt={item.label}
                  fill
                  className="object-contain"
                  priority
                />
              </div>
              <span className="text-[16px] leading-none pt-[1px]">{item.label}</span>
            </Link>
          )
        })}
      </div>
    </nav>
  )
}
