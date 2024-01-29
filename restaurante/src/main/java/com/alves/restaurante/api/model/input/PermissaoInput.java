package com.alves.restaurante.api.model.input;

import jakarta.validation.constraints.NotBlank;

public class PermissaoInput {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
}
