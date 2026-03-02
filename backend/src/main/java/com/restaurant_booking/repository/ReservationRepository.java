package com.restaurant_booking.repository;

import com.restaurant_booking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationCode(String reservationCode);

    /**
     * Finds reservations that overlap with a given time range for a specific table.
     *
     * Overlap condition:
     * existing.startTime < newEndTime AND existing.endTime > newStartTime
     */
    @Query("""
        SELECT r FROM Reservation r
        WHERE r.tableId = :tableId
          AND r.startTime < :newEnd
          AND r.endTime > :newStart
    """)
    List<Reservation> findOverlappingReservations(
            @Param("tableId") Long tableId,
            @Param("newStart") LocalDateTime newStart,
            @Param("newEnd") LocalDateTime newEnd
    );
}