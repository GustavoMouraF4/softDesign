package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.entity.StaveSessionEntity;
import com.gustavomoura.softdesign.exception.NotFoundSessionException;
import com.gustavomoura.softdesign.exception.StaveSessionExpiredException;
import com.gustavomoura.softdesign.repository.StaveSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class StaveSessionService {

    private final StaveSessionRepository repository;
    private final StaveService staveService;

    /**
     *
     * @param entity session's information
     * @return return a session opened
     */
    public StaveSessionEntity createSession(StaveSessionEntity entity) {
        var sessionId = UUID.randomUUID().toString();
        entity.setId(sessionId);
        staveService.validateStave(entity.getStaveId());

        log.info("starting a session with id: " + sessionId);

        return repository.save(entity);
    }

    /**
     * this method validate if session time is already expired
     * @param staveId id of the stave
     */
    public void validateSession(String staveId) {
        var session = findSessionByStaveId(staveId);

        log.info("Checking if a session with stave id: " + staveId + " is already expired");

        if (session.getCreatedAt().plus(session.getOpenSessionTime(), ChronoUnit.MINUTES).isBefore(LocalDateTime.now())) {
            throw new StaveSessionExpiredException("Session with id: " + session.getId() + " already expired!");
        }
    }

    /**
     *
     * @param staveId id of the stave
     * @return return session entity
     */
    private StaveSessionEntity findSessionByStaveId(String staveId) {
        log.info("Searching session with stave id: " + staveId);

        return repository.findByStaveId(staveId)
                .orElseThrow(() -> new NotFoundSessionException("Session with stave id: " + staveId + " not found!"));
    }
}
