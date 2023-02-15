package com.gustavomoura.softdesign.repository;

import com.gustavomoura.softdesign.entity.VoteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, String> {

    @Query("SELECT count(v) FROM VoteEntity v WHERE v.staveId = :staveId AND v.hisVote IS :hisVote")
    Long count(@Param("staveId") String staveId, @Param("hisVote") boolean hisVote);

    List<VoteEntity> findAllByStaveId(String staveId);
}
