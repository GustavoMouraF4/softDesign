package com.gustavomoura.softdesign.controller;

import com.gustavomoura.softdesign.dto.StaveMapper;
import com.gustavomoura.softdesign.dto.StaveSessionRequest;
import com.gustavomoura.softdesign.dto.StaveSessionResponse;
import com.gustavomoura.softdesign.service.StaveSessionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaveSessionController {

    private final StaveSessionService service;
    private final StaveMapper mapper;

    @Operation(method = "open a new session")
    @RequestMapping(value = "/v1/session", method = RequestMethod.POST)
    public ResponseEntity<StaveSessionResponse> startSession(@RequestBody StaveSessionRequest request) {
        var entity = mapper.toEntity(request);
        return new ResponseEntity<>(mapper.toResponse(service.createSession(entity)), HttpStatus.CREATED);
    }
}
