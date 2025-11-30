package com.monbillet.monbillet.repository;

import com.monbillet.monbillet.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
