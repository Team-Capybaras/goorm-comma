import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  reactCompiler: true,
  images: {
    domains: [
      'goorm-shopping-s3.s3.ap-northeast-2.amazonaws.com',
    ],
    formats: ['image/avif', 'image/webp'],
  },
};

export default nextConfig;
