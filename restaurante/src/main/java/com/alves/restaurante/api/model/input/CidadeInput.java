package com.alves.restaurante.api.model.input;

import com.alves.restaurante.api.model.EstadoModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoInput estado;
}
