package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.entity.StaveEntity;
import com.gustavomoura.softdesign.exception.NotFoundStaveException;
import com.gustavomoura.softdesign.exception.StaveVotedException;
import com.gustavomoura.softdesign.repository.StaveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class StaveService {

    private final StaveRepository repository;

    /**
     * @param description description of the stave
     * @return return a new stave
     */
    public StaveEntity createStave(String description) {
        var entityToSave = StaveEntity.builder()
                .id(UUID.randomUUID().toString())
                .description(description)
                .build();

        log.info("Creating a stave...");

        return repository.save(entityToSave);
    }

    /**
     *
     * @param staveId id of the stave
     * @return return the stave entity
     */
    public StaveEntity findStaveById(String staveId) {
        log.info("Searching a stave with id: " + staveId);

        return repository.findById(staveId)
                .orElseThrow(() -> new NotFoundStaveException("Stave with id: " + staveId + " not found!"));
    }

    /**
     * this method validate if a stave has be voted
     * @param staveId id of the stave
     */
    public void validateStave(String staveId) {
        var staveToValidate = findStaveById(staveId);

        log.info("validating a stave with id: " + staveId);

        if (staveToValidate.isStaveVoted()) {
            throw new StaveVotedException("Stave with id: " + staveId + " already voted!");
        }
    }

    /**
     *
     * @param staveId id of the stave that will be updated
     * @param stave stave's data to be updated
     * @return stave that was updated
     */
    public StaveEntity updateStave(String staveId, StaveEntity stave) {
        var staveToUpdate = findStaveById(staveId);

        staveToUpdate.setStaveVoted(stave.isStaveVoted());
        staveToUpdate.setResult(stave.getResult());
        staveToUpdate.setStaveApproved(stave.isStaveApproved());

        log.info("updating a stave with id: " + staveId);

        return repository.save(staveToUpdate);
    }
}
