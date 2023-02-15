package com.gustavomoura.softdesign.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "stave_session")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveSessionEntity {

    @Id
    @Column(name = "stave_session_id")
    private String id;

    @Column(name = "stave_id")
    private String staveId;

    @Column(name = "open_session_time")
    private int openSessionTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
