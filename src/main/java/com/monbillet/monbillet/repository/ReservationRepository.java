package com.monbillet.monbillet.repository;

import com.monbillet.monbillet.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
