import './globals.css'
import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import Link from 'next/link'
import { getCurrentUser, logout } from './actions'
import { Button } from "@/components/ui/button"

const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Forum App',
  description: 'A forum application with bulletin boards and user authentication',
}

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const user = await getCurrentUser()

  return (
    <html lang="en">
      <body className={`${inter.className} bg-gray-100 min-h-screen`}>
        <div className="max-w-[1920px] mx-auto px-4 py-8">
          <header className="bg-white shadow rounded-lg mb-8 p-6">
            <div className="flex justify-between items-center">
              <h1 className="text-3xl font-bold text-gray-800">Forum App</h1>
              <nav>
                <ul className="flex space-x-6 text-lg">
                  <li>
                    <Link href="/" className="text-blue-500 hover:text-blue-700">Home</Link>
                  </li>
                  {user ? (
                    <>
                      <li>
                        <Link href="/my-page" className="text-blue-500 hover:text-blue-700">My Page</Link>
                      </li>
                      <li className="text-gray-600">Welcome, {user.name}!</li>
                      <li>
                        <form action={logout}>
                          <Button type="submit" variant="ghost" className="text-lg">Logout</Button>
                        </form>
                      </li>
                    </>
                  ) : (
                    <li>
                      <Link href="/auth" className="text-blue-500 hover:text-blue-700">Login/Signup</Link>
                    </li>
                  )}
                </ul>
              </nav>
            </div>
          </header>
          <main>{children}</main>
        </div>
      </body>
    </html>
  )
}

