package com.gustavomoura.softdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {

    @Id
    @Column(name = "vote_id")
    private String id;


    @Column(name = "stave_id")
    private String staveId;

    @Column(name = "associate_id")
    private String associateId;

    @Column(name = "his_vote")
    private boolean hisVote;
}
