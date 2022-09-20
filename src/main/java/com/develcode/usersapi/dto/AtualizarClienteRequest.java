package com.develcode.usersapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtualizarClienteRequest {
    private Long id;
    private String nome;
    private String dataNascimento;
    private byte[] foto;

}
