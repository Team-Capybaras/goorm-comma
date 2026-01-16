import { NextResponse } from 'next/server';

export async function GET(req: Request) {
  const { searchParams } = new URL(req.url);
  const areaCode = searchParams.get('area_code');

  const res = await fetch(
    `${process.env.API_BASE_URL}/v1/avoidance/statistics?area_code=${areaCode}`
  );

  const data = await res.json();
  return NextResponse.json(data);
}