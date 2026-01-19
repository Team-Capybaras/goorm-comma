import { NextResponse } from 'next/server'

export async function GET(req: Request) {

  // 위치 가져오기
  try {
    const { searchParams } = new URL(req.url)
    const lat = searchParams.get('lat')
    const lng = searchParams.get('lng')

    // 위치 없으면 400 에러
    if (!lat || !lng) {
      return NextResponse.json(
        { error: 'lat, lng required' },
        { status: 400 }
      )
    }

    // 위치가 있으면 카카오 API 불러오고 불러왔는지 판별
    const key = process.env.KAKAO_REST_KEY
    if (!key) {
      return NextResponse.json(
        { error: 'KAKAO_REST_KEY not set' },
        { status: 500 }
      )
    }

    // 불러왔으면 위도, 경도를 현재 동 위치로 전환
    const res = await fetch(
      `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`,
      {
        headers: {
          Authorization: `KakaoAK ${key}`,
        },
      }
    )

    // 불러왔는데 접근이 안된다면 에러 처리
    if (!res.ok) {
      const text = await res.text()
      console.error('Kakao error:', text)
      return NextResponse.json(
        { error: 'Kakao API error', detail: text },
        { status: 502 }
      )
    }

    const data = await res.json()

    const address = data.documents?.[0]?.address
    const locationName =
      address?.region_3depth_name ||
      address?.region_2depth_name ||
      address?.region_1depth_name ||
      null

    return NextResponse.json({ locationName })
  } catch (e) {
    return NextResponse.json(
      { error: 'Internal server error' },
      { status: 500 }
    )
  }
}