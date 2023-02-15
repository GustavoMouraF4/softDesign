package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.client.CpfClient;
import com.gustavomoura.softdesign.client.CpfStatusEnum;
import com.gustavomoura.softdesign.entity.AssociateEntity;
import com.gustavomoura.softdesign.exception.InvalidCpfException;
import com.gustavomoura.softdesign.exception.NotFoundAssociateException;
import com.gustavomoura.softdesign.repository.AssociateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AssociateService {

    private final AssociateRepository repository;

    @Autowired
    private CpfClient cpfClient;

    /**
     *
     * @param associate associate's data to be created
     * @return associate that was created
     */
    public AssociateEntity createAssociate(AssociateEntity associate) {
        var entity = AssociateEntity.builder().build();

        log.info("Checking if the associate is already created...");

        if (!repository.existsByCpf(associate.getCpf())) {
            var associateId = UUID.randomUUID().toString();
            associate.setId(associateId);
            entity = repository.save(associate);

            log.info("Creating new associate with id: " + associateId);
        } else {
            entity = findAssociateByCpf(associate.getCpf());

            log.info("Member is already created by loading it...");
        }

        return entity;
    }

    /**
     *
     * @param cpf associate's cpf to be validating
     * @return
     */
    public boolean validateCpf(String cpf) {
        //TODO Por favor verificar se essa API esta funcionando, pois testei e sempre retorna status 404, mesmo gerando cpf em geradores como o 4Devs
        try {
            var cpfValidated = cpfClient.verifyCpf(cpf);

            log.info("Checking if the cpf is valid...");

            return cpfValidated.getStatus().equalsIgnoreCase(CpfStatusEnum.ABLE_TO_VOTE.name());
        } catch (Exception exception) {
            throw new InvalidCpfException("CPF " + cpf + "is invalid!");
        }

    }

    /**
     *
     * @param cpf associate's cpf to be researching
     * @return return the associate entity
     */
     public AssociateEntity findAssociateByCpf(String cpf) {
         log.info("Finding an associate with the cpf: " + cpf);

        return repository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundAssociateException("Associate with cpf: " + cpf + " not found!"));
     }
}
