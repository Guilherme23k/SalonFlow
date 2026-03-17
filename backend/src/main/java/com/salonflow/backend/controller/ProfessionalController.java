package com.salonflow.backend.controller;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.controller.dtos.response.ProfessionalResponseDTO;
import com.salonflow.backend.service.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {


    @Autowired
    private ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<ProfessionalResponseDTO> createProfessional(@RequestBody @Valid ProfessionalCreateDTO dto){

        ProfessionalResponseDTO responseDTO = ProfessionalResponseDTO.fromCreateToResponse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

}
