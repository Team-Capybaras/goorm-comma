import KakaoMap from '@/components/common/KakaoMap'
import { Card, CardContent, CardFooter, CardHeader } from '@/components/common/Card'

export default function page() {
  return (
    <>
      <Card>
        <CardHeader
          left={<div>좌측설정</div>}
          right={<div>우측설정</div>}
          closable // 닫기 버튼 사용 시 추가
        >
          카드헤더 내용 설정
        </CardHeader>
        <CardContent>
          카드 내용 설정
        </CardContent>
        <CardContent center={<div>카드 내용 중앙 설정</div>} />
        <CardFooter>
          카드 푸터 내용 설정
        </CardFooter>
        <CardFooter center={<div>카드 푸터 중앙 설정</div>} />
      </Card>
      <div className="h-[500px] w-[800px]">
        <KakaoMap />
      </div>
    </>
  )
}
