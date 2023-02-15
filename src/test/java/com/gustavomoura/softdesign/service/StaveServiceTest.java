package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.entity.StaveEntity;
import com.gustavomoura.softdesign.exception.NotFoundStaveException;
import com.gustavomoura.softdesign.exception.StaveVotedException;
import com.gustavomoura.softdesign.repository.StaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StaveServiceTest {

    private StaveRepository repository;
    private StaveService service;

    @BeforeEach
    void setUp() {
        repository = mock(StaveRepository.class);
        service = new StaveService(repository);
    }

    @Test
    void shouldCreateStave() {
        var staveName = "random name";
        var staveId = UUID.randomUUID().toString();

        when(repository.save(any()))
                .thenAnswer(invocation -> {
                    var entityCreated = (StaveEntity) invocation.getArgument(0);
                    entityCreated.setId(staveId);
                    entityCreated.setCreatedAt(LocalDateTime.MAX);
                    return entityCreated;
                });

        var result = service.createStave(staveName);

        assertEquals(result.getId(), staveId);
        assertEquals(result.getDescription(), staveName);
        assertEquals(result.getCreatedAt(), LocalDateTime.MAX);
    }

    @Test
    void shouldFindStaveById() {
        var staveId = UUID.randomUUID().toString();
        var stave = createStaveEntity();
        stave.setId(staveId);

        when(repository.findById(staveId))
                .thenReturn(Optional.of(stave));

        var result = service.findStaveById(staveId);

        assertEquals(result.getId(), staveId);
        assertEquals(result.getDescription(), stave.getDescription());
        assertEquals(result.getResult(), stave.getResult());
        assertEquals(result.getCreatedAt(), stave.getCreatedAt());
        assertFalse(result.isStaveApproved());
        assertFalse(result.isStaveVoted());
    }

    @Test
    void shouldThrowWhenFindStaveById() {
        var staveId = UUID.randomUUID().toString();
        var stave = createStaveEntity();
        stave.setId(staveId);

        when(repository.findById(staveId))
                .thenThrow(NotFoundStaveException.class);

        assertThrows(NotFoundStaveException.class, () -> service.findStaveById(staveId));
    }

    @Test
    void shouldThrowWhenValidateStave() {
        var staveId = UUID.randomUUID().toString();
        var stave = createStaveEntity();
        stave.setId(staveId);
        stave.setStaveVoted(true);

        when(repository.findById(staveId))
                .thenReturn(Optional.of(stave));

        assertThrows(StaveVotedException.class, () -> service.validateStave(staveId));
    }

    @Test
    void shouldUpdateStave() {
        var staveId = UUID.randomUUID().toString();
        var stave = createStaveEntity();
        stave.setId(staveId);

        var staveData = createStaveEntity();
        staveData.setStaveApproved(true);
        staveData.setStaveVoted(true);
        staveData.setResult(50);
        staveData.setId(staveId);

        when(repository.findById(staveId))
                .thenReturn(Optional.of(stave));

        when(repository.save(any()))
                .thenReturn(staveData);

        var result = service.updateStave(staveId, staveData);

        assertEquals(result.getId(), staveId);
        assertEquals(result.getDescription(), stave.getDescription());
        assertEquals(result.getResult(), stave.getResult());
        assertEquals(result.getCreatedAt(), stave.getCreatedAt());
        assertTrue(result.isStaveApproved());
        assertTrue(result.isStaveVoted());
    }

    private StaveEntity createStaveEntity() {
        return StaveEntity.builder()
                .description("description")
                .result(20)
                .staveVoted(false)
                .staveApproved(false)
                .createdAt(LocalDateTime.MAX)
                .build();
    }
}