import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import BookingPage from "./pages/BookingPage";
import MenuPage from "./pages/MenuPage";
import ReservationLookupPage from "./pages/ReservationLookupPage";

export default function App() {
  return (
    <BrowserRouter>
      <div className="app-shell">
        <Navbar />
        <main className="page-shell">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/booking" element={<BookingPage />} />
            <Route path="/menu" element={<MenuPage />} />
            <Route path="/reservation" element={<ReservationLookupPage />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}