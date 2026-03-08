import { Link, useLocation } from "react-router-dom";

export default function Navbar() {
  const location = useLocation();

  const isActive = (path) => location.pathname === path;

  return (
    <header className="site-header">
      <div className="site-header-inner">
        <div className="brand-block">
          <span className="brand-kicker">Restaurant Booking</span>
          <h1 className="brand-title">La Pergola</h1>
        </div>

        <nav className="main-nav">
          <Link className={isActive("/") ? "nav-link active" : "nav-link"} to="/">
            Home
          </Link>
          <Link className={isActive("/booking") ? "nav-link active" : "nav-link"} to="/booking">
            Book a Table
          </Link>
          <Link className={isActive("/menu") ? "nav-link active" : "nav-link"} to="/menu">
            Menu
          </Link>
          <Link
            className={isActive("/reservation") ? "nav-link active" : "nav-link"}
            to="/reservation"
          >
            Find Reservation
          </Link>
        </nav>
      </div>
    </header>
  );
}