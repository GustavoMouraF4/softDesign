package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.entity.VoteEntity;
import com.gustavomoura.softdesign.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoteServiceTest {

    private VoteRepository repository;
    private StaveService staveService;
    private AssociateService associateService;
    private StaveSessionService staveSessionService;
    private VoteService service;

    @BeforeEach
    void setUp() {
        repository = mock(VoteRepository.class);
        staveService = mock(StaveService.class);
        associateService = mock(AssociateService.class);
        staveSessionService = mock(StaveSessionService.class);
        service = new VoteService(repository, staveService, associateService, staveSessionService);
    }

    @Test
    void shouldCreateVote() {
        var staveId = UUID.randomUUID().toString();
        var associateId = UUID.randomUUID().toString();
        var voteId = UUID.randomUUID().toString();
        var vote = createVoteEntity(staveId, associateId);

        when(repository.save(vote))
                .thenAnswer(invocation -> {
                    VoteEntity entity = invocation.getArgument(0);
                    entity.setId(voteId);

                    return entity;
                });

        var result = service.createVote(vote);

        assertEquals(result.getId(), voteId);
        assertEquals(result.getStaveId(), staveId);
        assertEquals(result.getAssociateId(), associateId);
        assertFalse(result.isHisVote());

    }

    @Test
    void shouldListVotesByStaveId() {
        var staveId = UUID.randomUUID().toString();
        var firstAssociateId = UUID.randomUUID().toString();
        var firstVoteId = UUID.randomUUID().toString();
        var firstVote = createVoteEntity(staveId, firstAssociateId);
        firstVote.setId(firstVoteId);

        var secondAssociateId = UUID.randomUUID().toString();
        var secondVoteId = UUID.randomUUID().toString();
        var secondVote = createVoteEntity(staveId, secondAssociateId);
        secondVote.setId(secondVoteId);

        when(repository.findAllByStaveId(staveId))
                .thenReturn(List.of(firstVote, secondVote));

        var result = service.listVotesByStaveId(staveId);

        assertTrue(result.stream().allMatch(vote -> Objects.nonNull(vote.getId()) &&
                Objects.nonNull(vote.getStaveId()) &&
                Objects.nonNull(vote.getAssociateId()) &&
                !vote.isHisVote()));
    }

    private VoteEntity createVoteEntity(String staveId, String associateId) {
        return VoteEntity.builder()
                .staveId(staveId)
                .associateId(associateId)
                .hisVote(false)
                .build();
    }
}