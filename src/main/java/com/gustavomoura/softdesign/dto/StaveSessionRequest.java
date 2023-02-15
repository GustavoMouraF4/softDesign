package com.gustavomoura.softdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveSessionRequest {

    private String staveId;
    private Integer openSessionTime;
}
