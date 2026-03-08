import { useEffect, useState } from "react";
import api from "../api/api";

export default function BookingPage() {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);
  const [startTime, setStartTime] = useState("");
  const [durationHours, setDurationHours] = useState(2);
  const [groupSize, setGroupSize] = useState(2);
  const [zone, setZone] = useState("");
  const [availability, setAvailability] = useState({});
  const [reservationCode, setReservationCode] = useState(null);
  const [dishOfTheDay, setDishOfTheDay] = useState(null);
  const [includeDishOfTheDay, setIncludeDishOfTheDay] = useState(false);

  useEffect(() => {
    api.get("/tables")
      .then((response) => {
        setTables(response.data);
      })
      .catch((error) => {
        console.error("Error fetching tables:", error);
      });
  }, []);

  useEffect(() => {
    if (!startTime) return;

    api.get("/tables/availability", {
      params: {
        startTime,
        durationHours: Number(durationHours)
      }
    })
      .then((response) => {
        const map = {};
        response.data.forEach((item) => {
          map[item.tableId] = item.available;
        });
        setAvailability(map);
      })
      .catch((error) => {
        console.error("Error fetching availability:", error);
      });

    const selectedDate = startTime.split("T")[0];

    api.get("/menu/dish-of-the-day", {
      params: {
        date: selectedDate
      }
    })
      .then((response) => {
        setDishOfTheDay(response.data);
      })
      .catch((error) => {
        console.error("Error fetching dish of the day:", error);
      });
  }, [startTime, durationHours]);

  const recommendTable = async () => {
    if (!startTime) {
      alert("Please choose a date and time before requesting a recommendation.");
      return;
    }

    if (!durationHours) {
      alert("Please choose reservation duration.");
      return;
    }

    if (!groupSize || groupSize < 1) {
      alert("Please enter a valid group size.");
      return;
    }

    try {
      const response = await api.post("/recommend", {
        startTime,
        durationHours: Number(durationHours),
        groupSize: Number(groupSize),
        nearWindow: false,
        quietArea: zone === "QUIET_AREA",
        zone: zone
      });

      setSelectedTable(response.data.tableId);
    } catch (error) {
      if (error.response?.status === 404) {
        alert("No suitable table available for the selected time and group size.");
      } else if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("Failed to get recommendation.");
      }
    }
  };

  const createReservation = async () => {
    if (!startTime) {
      alert("Please choose a reservation time.");
      return;
    }

    if (!selectedTable) {
      alert("Please select a table first.");
      return;
    }

    try {
      const response = await api.post("/reservations", {
        tableId: selectedTable,
        startTime: startTime,
        durationHours: Number(durationHours),
        includesDishOfTheDay: includeDishOfTheDay
      });

      setReservationCode(response.data.reservationCode);
    } catch (error) {
      if (error.response?.status === 409) {
        alert("This table is already reserved for that time. Please choose another time.");
      } else if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("Reservation failed.");
      }
    }
  };

  const getTableColor = (tableId) => {
    if (selectedTable === tableId) return "#4CAF50";
    if (availability[tableId] === false) return "#D9534F";
    return "#444";
  };

  const visibleTables = tables.filter((table) => {
    if (!zone) return true;
    return table.zone === zone;
  });

  return (
    <div className="content-stack">
      <section className="section-card centered-card">
        <span className="section-kicker">Choose your experience</span>
        <h1 className="page-title">Book a Table</h1>
      </section>

      <section className="section-card">
        {dishOfTheDay && (
          <div className="dish-highlight">
            <div>
              <span className="section-kicker">Dish of the Day</span>
              <h2>{dishOfTheDay.name}</h2>
              <p>{dishOfTheDay.description}</p>
            </div>

            <label className="checkbox-row">
                <input
                    type="checkbox"
                    checked={includeDishOfTheDay}
                    onChange={(e) => setIncludeDishOfTheDay(e.target.checked)}
                />
                <span>Include Dish of the Day in reservation</span>
            </label>
          </div>
        )}

        <div className="filters-grid">
          <div>
            <label>Start Time</label>
            <input
              type="datetime-local"
              value={startTime}
              onChange={(e) => setStartTime(e.target.value)}
            />
          </div>

          <div>
            <label>Duration (hours)</label>
            <input
              type="number"
              value={durationHours}
              onChange={(e) => setDurationHours(e.target.value)}
              min="1"
              max="6"
            />
          </div>

          <div>
            <label>Group Size</label>
            <input
              type="number"
              value={groupSize}
              onChange={(e) => setGroupSize(e.target.value)}
              min="1"
            />
          </div>

          <div>
            <label>Area</label>
            <select value={zone} onChange={(e) => setZone(e.target.value)}>
              <option value="">All Areas</option>
              <option value="MAIN_HALL">Main Hall</option>
              <option value="QUIET_AREA">Quiet Area</option>
              <option value="PATIO">Patio</option>
              <option value="PARTY_ROOM">Party Room</option>
              <option value="PRIVATE_ROOM">Private Room</option>
            </select>
          </div>
        </div>

        <div className="center-actions">
          <button className="primary-btn" onClick={recommendTable}>
            Recommend Best Table
          </button>
        </div>
      </section>

      <section className="section-card">
        <h2 className="section-title center-text">Restaurant Floor Plan</h2>

        <div className="floorplan-scroll">
          <div className="floorplan-board">
            <div className="reception-box">Reception</div>

            <div className="zone-box zone-mainhall">
              <span>Main Hall</span>
            </div>

            <div className="zone-box zone-quiet">
              <span>Quiet Area</span>
            </div>

            <div className="zone-box zone-private">
              <span>Private Room</span>
            </div>

            <div className="zone-box zone-patio">
              <span>Patio</span>
            </div>

            <div className="zone-box zone-party">
              <span>Party Room</span>
            </div>

            {visibleTables.map((table) => (
              <div
                key={table.id}
                title={`${table.tableLabel} • ${table.zone} • ${table.capacity} seats`}
                className={`table-node ${selectedTable === table.id ? "selected" : ""} ${
                  availability[table.id] === false ? "reserved" : ""
                } ${table.capacity >= 10 ? "large" : ""}`}
                style={{
                  left: table.posX,
                  top: table.posY,
                  cursor: availability[table.id] === false ? "not-allowed" : "pointer"
                }}
                onClick={() => {
                  if (availability[table.id] === false) return;
                  setSelectedTable(table.id);
                }}
              >
                <div>{table.tableLabel}</div>
                <div>{table.capacity}p</div>
              </div>
            ))}
          </div>
        </div>

        <div className="legend-row">
          <span>● Available</span>
          <span className="legend-selected">● Selected</span>
          <span className="legend-reserved">● Reserved</span>
        </div>

        {selectedTable && (
          <div className="center-actions" style={{ marginTop: "24px" }}>
            <button className="primary-btn" onClick={createReservation}>
              Reserve Table {selectedTable}
            </button>
          </div>
        )}

        {reservationCode && (
          <div className="success-box">
            <h2>Reservation Created!</h2>
            <p>Your reservation code is:</p>
            <h3>{reservationCode}</h3>
            <p>
              Please save this code. You will need it to view or modify your reservation later.
            </p>
            <button
              className="primary-btn"
              onClick={() => navigator.clipboard.writeText(reservationCode)}
            >
              Copy Reservation Code
            </button>
          </div>
        )}
      </section>
    </div>
  );
}