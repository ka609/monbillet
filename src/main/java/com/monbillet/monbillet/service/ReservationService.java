package com.monbillet.monbillet.service;

import com.monbillet.monbillet.entity.Reservation;
import com.monbillet.monbillet.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /** Récupérer toutes les réservations */
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /** Récupérer une réservation par ID */
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Réservation introuvable avec l'ID : " + id));
    }

    /** Enregistrer ou modifier une réservation */
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /** Supprimer une réservation */
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : aucune réservation trouvée avec l'ID : " + id);
        }
        reservationRepository.deleteById(id);
    }
}
