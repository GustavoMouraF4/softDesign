package com.gustavomoura.softdesign.service;

import com.gustavomoura.softdesign.client.CpfClient;
import com.gustavomoura.softdesign.client.CpfStatusEnum;
import com.gustavomoura.softdesign.dto.CpfEntity;
import com.gustavomoura.softdesign.entity.AssociateEntity;
import com.gustavomoura.softdesign.exception.InvalidCpfException;
import com.gustavomoura.softdesign.exception.NotFoundAssociateException;
import com.gustavomoura.softdesign.repository.AssociateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssociateServiceTest {

    private AssociateRepository repository;
    private CpfClient cpfClient;
    private AssociateService service;

    @BeforeEach
    void setUp() {
        repository = mock(AssociateRepository.class);
        cpfClient = mock(CpfClient.class);
        service = new AssociateService(repository, cpfClient);
    }

    @Test
    void shouldCreateAssociate() {
        var associateId = UUID.randomUUID().toString();
        var associate = createAssociateEntity();

        when(repository.existsByCpf(associate.getCpf()))
                .thenReturn(false);

        when(repository.save(associate))
                .thenAnswer(invocation -> {
                    AssociateEntity entity = invocation.getArgument(0);
                    entity.setId(associateId);

                    return entity;
                });

        var result = service.createAssociate(associate);

        assertEquals(result.getId(), associateId);
        assertEquals(result.getCpf(), associate.getCpf());
    }

    @Test
    void shouldNotCreateAssociateWhenExists() {
        var associateId = UUID.randomUUID().toString();
        var associate = createAssociateEntity();
        associate.setId(associateId);

        when(repository.existsByCpf(associate.getCpf()))
                .thenReturn(true);

        when(repository.findByCpf(associate.getCpf()))
                .thenReturn(Optional.of(associate));

        var result = service.createAssociate(associate);

        assertEquals(result.getId(), associateId);
        assertEquals(result.getCpf(), associate.getCpf());
    }

    @Test
    void shouldThrowWhenValidateCpfReturnUnableToVote() {
        var associate = createAssociateEntity();

        when(cpfClient.verifyCpf(associate.getCpf()))
                .thenThrow(InvalidCpfException.class);

        assertThrows(InvalidCpfException.class, () -> service.validateCpf(associate.getCpf()));
    }

    @Test
    void shouldFindAssociateByCpf() {
        var associateId = UUID.randomUUID().toString();
        var associate = createAssociateEntity();
        associate.setId(associateId);

        when(repository.findByCpf(associate.getCpf()))
                .thenReturn(Optional.of(associate));

        var result = service.findAssociateByCpf(associate.getCpf());

        assertEquals(result.getId(), associateId);
        assertEquals(result.getCpf(), associate.getCpf());
    }

    @Test
    void shouldThrowWhenNotFoundAssociate() {
        var associate = createAssociateEntity();

        when(repository.findByCpf(associate.getCpf()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundAssociateException.class, () -> service.findAssociateByCpf(associate.getCpf()));
    }

    private AssociateEntity createAssociateEntity() {
        return AssociateEntity.builder()
                .cpf("12345678901")
                .build();
    }
}