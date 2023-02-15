package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.dto.StaveVoteRequest;
import com.gustavomoura.softdesign.entity.AssociateEntity;
import com.gustavomoura.softdesign.entity.StaveEntity;
import com.gustavomoura.softdesign.entity.VoteEntity;
import com.gustavomoura.softdesign.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository repository;
    private final StaveService staveService;
    private final AssociateService associateService;
    private final StaveSessionService staveSessionService;

    /**
     * this method analyzes the votes of the stave and saves them in the bank with all the necessary validations
     * @param request Associate's votes
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void loadVotes(StaveVoteRequest request) {
        var staveId = request.getStaveId();

        log.info("loading stave's votes...");

        AtomicReference<Integer> voteCount = new AtomicReference<>(0);
        request.getVotes().stream()
                .filter(vote -> associateService.validateCpf(vote.getCpf()))//TODO caso esse filter não esteja funcionando, por favor remove-lo
                .forEach(vote -> {
                    staveSessionService.validateSession(staveId);

                    var associateEntity = AssociateEntity.builder()
                            .cpf(vote.getCpf())
                            .build();
                    var associate = associateService.createAssociate(associateEntity);

                    voteCount.getAndSet(voteCount.get() + 1);
                    var voteToCreate = VoteEntity.builder()
                            .staveId(staveId)
                            .associateId(associate.getId())
                            .hisVote(vote.getHisVote().equalsIgnoreCase("SIM"))
                            .build();
                    createVote(voteToCreate);
                });

        var verifyIfStaveHasApproved = repository.count(staveId, true) >= repository.count(staveId, false);
        var staveToUpdate = StaveEntity.builder()
                .staveVoted(true)
                .result(voteCount.get())
                .staveApproved(verifyIfStaveHasApproved)
                .build();

        staveService.updateStave(staveId, staveToUpdate);
    }

    /**
     *
     * @param voteToCreate associate's vote that will be created
     * @return return the vote created
     */
    public VoteEntity createVote(VoteEntity voteToCreate) {
        var voteId = UUID.randomUUID().toString();
        voteToCreate.setId(voteId);

        log.info("Creating a vote with id: " + voteId);

        return repository.save(voteToCreate);
    }

    public List<VoteEntity> listVotesByStaveId(String staveId) {
        return repository.findAllByStaveId(staveId);
    }
}
