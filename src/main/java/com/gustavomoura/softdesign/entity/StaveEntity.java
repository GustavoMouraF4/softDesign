package com.gustavomoura.softdesign.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "stave")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveEntity {

    @Id
    @Column(name = "stave_id")
    private String id;

    private String description;

    private Integer result;

    @Column(name = "stave_voted")
    private boolean staveVoted;

    @Column(name = "stave_approved")
    private boolean staveApproved;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
