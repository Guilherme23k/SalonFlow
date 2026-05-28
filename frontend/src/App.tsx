import { BrowserRouter, Route, Routes } from "react-router-dom"
import BookingPage from "./pages/BookingPage"

export default function App(){
  return (
    <BrowserRouter>
    <Routes>
      <Route path="/:slug" element={<BookingPage />} />
      <Route
      path="*"
      element={
        <div className="min-h-screen bg-zinc-950 flex items-center justify-center">
          <p className="text-zinc-400 text-lg">Salão não encontrado</p>
        </div>
      }
      />
    </Routes>
    </BrowserRouter>
  )
}
