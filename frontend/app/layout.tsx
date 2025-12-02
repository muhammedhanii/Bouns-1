import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Smart Parking Management System",
  description: "Smart Parking Management System with Command Pattern and MVC Architecture",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="antialiased">
        {children}
      </body>
    </html>
  );
}
