package com.gustavomoura.softdesign.dto;

import com.gustavomoura.softdesign.entity.StaveEntity;
import com.gustavomoura.softdesign.entity.StaveSessionEntity;
import com.gustavomoura.softdesign.entity.VoteEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StaveMapper {
    public StaveResponse toResponse(StaveEntity stave) {
        return StaveResponse.builder()
                .id(stave.getId())
                .description(stave.getDescription())
                .createdAt(stave.getCreatedAt())
                .build();
    }

    public StaveSessionEntity toEntity(StaveSessionRequest request) {
        return StaveSessionEntity.builder()
                .staveId(request.getStaveId())
                .openSessionTime(Objects.isNull(request.getOpenSessionTime()) ? 1 : request.getOpenSessionTime())
                .build();
    }

    public StaveSessionResponse toResponse(StaveSessionEntity session) {
        return StaveSessionResponse.builder()
                .sessionId(session.getId())
                .staveId(session.getStaveId())
                .openSessionTime(session.getOpenSessionTime())
                .createdAt(session.getCreatedAt())
                .build();
    }

    public VoteResponse toResponse(VoteEntity vote) {
        return VoteResponse.builder()
                .id(vote.getId())
                .staveId(vote.getStaveId())
                .associateId(vote.getAssociateId())
                .hisVote(vote.isHisVote() ? "Sim" : "Não")
                .build();
    }
}
