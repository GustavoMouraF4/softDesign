package com.gustavomoura.softdesign.repository;

import com.gustavomoura.softdesign.entity.AssociateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepository extends CrudRepository<AssociateEntity, String> {
    boolean existsByCpf(String cpf);

    Optional<AssociateEntity> findByCpf(String cpf);
}
