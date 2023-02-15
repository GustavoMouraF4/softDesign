package com.gustavomoura.softdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {

    private String id;
    private String staveId;
    private String associateId;
    private String cpf;
    private String hisVote;
}
