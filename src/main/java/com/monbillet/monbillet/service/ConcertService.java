package com.monbillet.monbillet.service;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    /** Récupérer tous les concerts */
    @Transactional(readOnly = true)
    public List<Concert> findAll() {
        return concertRepository.findAll();
    }

    /** Récupérer un concert par ID */
    @Transactional(readOnly = true)
    public Concert findById(Long id) {
        return concertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Concert introuvable avec l'ID : " + id
                ));
    }

    /** Ajouter ou mettre à jour un concert */
    public Concert save(Concert concert) {
        return concertRepository.save(concert);
    }

    /** Supprimer un concert par ID */
    public void deleteById(Long id) {
        if (!concertRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Impossible de supprimer : aucun concert trouvé avec l'ID : " + id
            );
        }
        concertRepository.deleteById(id);
    }
}
