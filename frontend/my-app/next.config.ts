import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
};

export default nextConfig;

module.exports = {
    async rewrites() {
        return [
            {
                source: "/api/:path*", // 프론트엔드의 API 경로
                destination: "http://localhost:8080/api/:path*", // 백엔드의 API 경로
            },
        ];
    },
};