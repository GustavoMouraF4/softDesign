package com.gustavomoura.softdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveResponse {

    private String id;
    private String description;
    private LocalDateTime createdAt;
}
