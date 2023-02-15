package com.gustavomoura.softdesign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaveVoteRequest {

    private String staveId;
    private List<VoteRequest> votes;
}
