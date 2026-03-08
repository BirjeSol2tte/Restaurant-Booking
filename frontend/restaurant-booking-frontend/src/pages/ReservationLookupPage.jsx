import { useState } from "react";
import api from "../api/api";

export default function ReservationLookupPage() {
  const [reservationCode, setReservationCode] = useState("");
  const [reservation, setReservation] = useState(null);

  const findReservation = async () => {
    if (!reservationCode) {
      alert("Please enter a reservation code.");
      return;
    }

    try {
      const response = await api.get(`/reservations/${reservationCode}`);
      setReservation(response.data);
    } catch (error) {
      if (error.response?.status === 404) {
        alert("Reservation not found.");
      } else if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("Failed to fetch reservation.");
      }
      setReservation(null);
    }
  };

  const cancelReservation = async () => {
    if (!reservationCode) {
      alert("Please enter a reservation code.");
      return;
    }

    const confirmed = window.confirm(
      "Are you sure you want to cancel this reservation?"
    );

    if (!confirmed) return;

    try {
      await api.delete(`/reservations/${reservationCode}`);
      alert("Reservation cancelled successfully.");
      setReservation(null);
      setReservationCode("");
    } catch (error) {
      console.error("Cancel reservation error:", error);

      if (error.response?.status === 404) {
        alert("Reservation not found.");
      } else if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else if (error.message) {
        alert("Failed to cancel reservation: " + error.message);
      } else {
        alert("Failed to cancel reservation.");
      }
    }
  };

  const updateDishChoice = async (includeDish) => {
    if (!reservationCode) {
      alert("Please enter a reservation code.");
      return;
    }

    try {
      const response = await api.put(`/reservations/${reservationCode}/dish`, {
        includesDishOfTheDay: includeDish
      });

      setReservation(response.data);

      if (includeDish) {
        alert("Dish of the Day added to reservation.");
      } else {
        alert("Dish of the Day removed from reservation.");
      }
    } catch (error) {
      if (error.response?.status === 404) {
        alert("Reservation not found.");
      } else if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("Failed to update dish choice.");
      }
    }
  };

  return (
    <div className="content-stack">
      <section className="section-card centered-card">
        <span className="section-kicker">Manage your booking</span>
        <h1 className="page-title">Find Reservation</h1>
        <p className="page-subtitle">
          Enter your reservation code to view or update your booking.
        </p>

        <div className="lookup-form">
          <input
            type="text"
            value={reservationCode}
            onChange={(e) => setReservationCode(e.target.value)}
            placeholder="Enter reservation code"
          />

          <button className="primary-btn" onClick={findReservation}>
            Find Reservation
          </button>
        </div>
      </section>

      {reservation && (
        <section className="section-card centered-card">
          <h2 className="section-title">Reservation Details</h2>

          <div className="details-grid">
            <div className="detail-box">
              <span>Code</span>
              <strong>
                {reservation.zone
                    ? reservation.zone.replaceAll("_", " ").toLowerCase().replace(/\b\w/g, c => c.toUpperCase())
                    : "-"}
            </strong>
            </div>
            <div className="detail-box">
              <span>Table Label</span>
              <strong>{reservation.tableLabel || "-"}</strong>
            </div>
            <div className="detail-box">
              <span>Area</span>
              <strong>{reservation.zone || "-"}</strong>
            </div>
            <div className="detail-box">
              <span>Start Time</span>
              <strong>{reservation.startTime}</strong>
            </div>
            <div className="detail-box">
              <span>End Time</span>
              <strong>{reservation.endTime}</strong>
            </div>
            <div className="detail-box">
              <span>Dish Included</span>
              <strong>{reservation.includesDishOfTheDay ? "Yes" : "No"}</strong>
            </div>
            <div className="detail-box">
              <span>Dish Name</span>
              <strong>{reservation.dishName ? reservation.dishName : "-"}</strong>
            </div>
          </div>

          <div
            style={{
              display: "flex",
              gap: "12px",
              justifyContent: "center",
              flexWrap: "wrap",
              marginTop: "24px"
            }}
          >
            <button className="primary-btn" onClick={() => updateDishChoice(true)}>
              Add Dish of the Day
            </button>

            <button className="secondary-btn" onClick={() => updateDishChoice(false)}>
              Remove Dish of the Day
            </button>
          </div>

          <button className="danger-btn" onClick={cancelReservation}>
            Cancel Reservation
          </button>
        </section>
      )}
    </div>
  );
}