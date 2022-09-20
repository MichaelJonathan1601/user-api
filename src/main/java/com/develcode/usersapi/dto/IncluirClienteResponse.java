package com.develcode.usersapi.dto;

import lombok.Data;

@Data
public class IncluirClienteResponse {
    private Long id;
    private String nome;
    private String dataNascimento;
    private byte[] foto;
}
