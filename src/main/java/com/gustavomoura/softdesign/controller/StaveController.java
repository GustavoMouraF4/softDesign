package com.gustavomoura.softdesign.controller;

import com.gustavomoura.softdesign.dto.StaveMapper;
import com.gustavomoura.softdesign.dto.StaveResponse;
import com.gustavomoura.softdesign.service.StaveService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaveController {

    private final StaveService service;
    private final StaveMapper mapper;

    @Operation(method = "create a new stave")
    @RequestMapping(value = "/v1/stave/{description}", method = RequestMethod.POST)
    public ResponseEntity<StaveResponse> createStave(@PathVariable String description) {
        return new ResponseEntity<>(mapper.toResponse(service.createStave(description)), HttpStatus.CREATED);
    }

    @Operation(method = "find a stave")
    @RequestMapping(value = "/v1/stave/{id}", method = RequestMethod.GET)
    public ResponseEntity<StaveResponse> findStaveById(@PathVariable String id) {
        return new ResponseEntity<>(mapper.toResponse(service.findStaveById(id)), HttpStatus.OK);
    }
}
