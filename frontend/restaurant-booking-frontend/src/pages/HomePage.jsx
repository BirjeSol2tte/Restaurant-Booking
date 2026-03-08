import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <div className="home-page">
      <section className="hero-card">
        <div className="hero-content">
          <span className="hero-kicker">Modern dining in the heart of the city</span>
          <h1>Welcome to La Pergola</h1>
          <p>
            Enjoy elegant dining, a relaxing patio, private rooms for special events,
            and a warm atmosphere for every occasion.
          </p>

          <div className="hero-actions">
            <Link to="/booking" className="primary-btn">
              Book a Table
            </Link>
            <Link to="/menu" className="secondary-btn">
              View Menu
            </Link>
          </div>
        </div>

        <div className="hero-side-card">
          <h3>Opening Hours</h3>
          <p>Mon–Thu: 12:00 – 22:00</p>
          <p>Fri–Sat: 12:00 – 23:30</p>
          <p>Sun: 13:00 – 21:00</p>

          <div className="hero-divider" />

          <h3>Contact</h3>
          <p>+372 5555 5555</p>
          <p>hello@lapergola.ee</p>
        </div>
      </section>

      <section className="info-grid">
        <article className="info-card">
          <h3>Main Hall</h3>
          <p>
            Comfortable everyday dining with tables for couples, families, and small groups.
          </p>
        </article>

        <article className="info-card">
          <h3>Quiet Area</h3>
          <p>
            A calmer section for intimate dinners, work lunches, and smaller reservations.
          </p>
        </article>

        <article className="info-card">
          <h3>Patio</h3>
          <p>
            Outdoor seating with a relaxed atmosphere and a lighter seasonal menu.
          </p>
        </article>

        <article className="info-card">
          <h3>Party & Private Rooms</h3>
          <p>
            Larger tables and private spaces for celebrations, business dinners, and events.
          </p>
        </article>
      </section>
    </div>
  );
}