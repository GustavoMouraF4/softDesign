package com.gustavomoura.softdesign.controller;

import com.gustavomoura.softdesign.dto.StaveMapper;
import com.gustavomoura.softdesign.dto.StaveVoteRequest;
import com.gustavomoura.softdesign.dto.VoteResponse;
import com.gustavomoura.softdesign.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService service;
    private final StaveMapper mapper;

    @Operation(method = "load votes to a stave")
    @RequestMapping(value = "/v1/vote", method = RequestMethod.POST)
    public ResponseEntity<Void> vote(@RequestBody StaveVoteRequest request) {
        service.loadVotes(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(method = "list votes by stave")
    @RequestMapping(value = "/v1/vote/{staveId}", method = RequestMethod.GET)
    public ResponseEntity<List<VoteResponse>> listVotesByStave(@PathVariable String staveId) {
        return new ResponseEntity<>(service.listVotesByStaveId(staveId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
