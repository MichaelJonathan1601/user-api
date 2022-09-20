package com.develcode.usersapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    private Long id;
    private String nome;
    private String dataNascimento;
    @Lob
    private byte[] foto;
}
