import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";
import React from "react";

export default function MainLayout({children}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <>
      <Header />
      <main>
        {children}
      </main>
      <Footer />
    </>
  )
}