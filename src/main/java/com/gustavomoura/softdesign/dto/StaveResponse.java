package com.gustavomoura.softdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveResponse {

    private String id;
    private String description;
    private Integer result;
    private String staveVoted;
    private String staveApproved;
    private LocalDateTime createdAt;
}
