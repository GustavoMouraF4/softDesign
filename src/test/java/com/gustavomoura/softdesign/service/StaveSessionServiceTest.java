package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.entity.StaveSessionEntity;
import com.gustavomoura.softdesign.exception.StaveSessionExpiredException;
import com.gustavomoura.softdesign.exception.StaveVotedException;
import com.gustavomoura.softdesign.repository.StaveSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StaveSessionServiceTest {

    private StaveSessionRepository repository;
    private StaveService staveService;
    private StaveSessionService service;

    @BeforeEach
    void setUp() {
        repository = mock(StaveSessionRepository.class);
        staveService = mock(StaveService.class);
        service = new StaveSessionService(repository, staveService);
    }

    @Test
    void shouldCreateSession() {
        var staveId = UUID.randomUUID().toString();
        var sessionId = UUID.randomUUID().toString();
        var session = createStaveSessionEntity(staveId);

        when(repository.save(session))
                .thenAnswer(invocation -> {
                    StaveSessionEntity entity = invocation.getArgument(0);
                    entity.setId(sessionId);

                    return entity;
                });

        var result = service.createSession(session);

        assertEquals(result.getId(), sessionId);
        assertEquals(result.getStaveId(), staveId);
        assertEquals(result.getOpenSessionTime(), session.getOpenSessionTime());
        assertEquals(result.getCreatedAt(), session.getCreatedAt());
    }

    @Test
    void shouldThrowWhenValidateSession() {
        var staveId = UUID.randomUUID().toString();
        var sessionId = UUID.randomUUID().toString();
        var session = createStaveSessionEntity(staveId);
        session.setCreatedAt(LocalDateTime.MIN);
        session.setId(sessionId);

        when(repository.findByStaveId(staveId))
                .thenReturn(Optional.of(session));

        assertThrows(StaveSessionExpiredException.class, () -> service.validateSession(staveId));
    }

    private StaveSessionEntity createStaveSessionEntity(String staveId) {
        return StaveSessionEntity.builder()
                .staveId(staveId)
                .openSessionTime(10)
                .createdAt(LocalDateTime.MAX)
                .build();
    }
}