'use client'

import { useState } from 'react'
import { Modal, ModalHeader, ModalContent } from '@/components/ui/Modal'
import Button from '@/components/common/Button'

export default function ModalForm() {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <>
      <Button 
        onClick={() => setIsOpen(true)}
        className="px-4 py-2 rounded bg-black text-white"
      >
        모달 열기
      </Button>
      <Modal
        open={isOpen}
        onClose={() => setIsOpen(false)}
        size="sm"
        closeOnBackdrop
        closeOnEscape
      >
        {/* 수동 여닫기 가능 */}
        <ModalHeader
          closable
          onClose={() => setIsOpen(false)}
        >
          <span className="text-xl font-bold">모달 테스트</span>
        </ModalHeader>
        <ModalContent className='p-2 text-card-foreground'>
          <p>이것은 모달입니다.</p>
        </ModalContent>
      </Modal>
    </>
  )
}