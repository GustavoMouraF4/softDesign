package com.gustavomoura.softdesign.repository;

import com.gustavomoura.softdesign.entity.StaveEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaveRepository extends CrudRepository<StaveEntity, String> {
}
