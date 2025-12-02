package com.monbillet.monbillet.service;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertService {

    private final ConcertRepository concertRepository;

    /** Récupérer tous les concerts */
    @Transactional(readOnly = true)
    public List<Concert> findAll() {
        return concertRepository.findAll();
    }

    /** Récupérer un concert par son ID */
    @Transactional(readOnly = true)
    public Concert findById(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Concert introuvable avec l'ID : " + id));
    }

    /** Ajouter ou modifier un concert */
    public Concert saveConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    /** Supprimer un concert */
    public void deleteConcert(Long id) {
        if (!concertRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : aucun concert trouvé avec l'ID : " + id);
        }
        concertRepository.deleteById(id);
    }
}
