package com.example.notification.controller;

import com.example.notification.dto.TemplateDTO;
import com.example.notification.global.mapper.Mapper;
import com.example.notification.input.TemplateInput;
import com.example.notification.model.TemplateMessage;
import com.example.notification.service.TemplateService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    private final Mapper mapper;

    @GetMapping("templates/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable UUID id) {
        TemplateMessage t = templateService.getTemplateById(id);
        return new ResponseEntity<>(mapper.map(t, TemplateDTO.class), HttpStatus.OK);
    }

    @PostMapping("templates")
    public ResponseEntity<TemplateDTO> getTemplateById(
            @RequestParam UUID userId,
            @RequestBody TemplateInput input) {
        TemplateMessage t = templateService.createTemplate(userId, input);
        return new ResponseEntity<>(mapper.map(t, TemplateDTO.class), HttpStatus.OK);
    }

    @GetMapping("{userId}/templates")
    public ResponseEntity<List<TemplateDTO>> getUserTemplates(
            @PathVariable @NonNull UUID userId,
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        List<TemplateMessage> templateMessages = templateService.getUserTemplates(userId, pageNum, pageSize, sortBy);
        List<TemplateDTO> dto = templateMessages.stream().map(t -> mapper.map(t, TemplateDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}
