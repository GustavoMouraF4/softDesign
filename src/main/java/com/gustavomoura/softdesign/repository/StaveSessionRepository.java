package com.gustavomoura.softdesign.repository;

import com.gustavomoura.softdesign.entity.StaveSessionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaveSessionRepository extends CrudRepository<StaveSessionEntity, String> {

    Optional<StaveSessionEntity> findByStaveId(String staveId);
}
