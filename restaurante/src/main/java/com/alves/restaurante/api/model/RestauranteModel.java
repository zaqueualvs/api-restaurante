package com.alves.restaurante.api.model;

import com.alves.restaurante.api.model.view.RestauranteView;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;
    @JsonView({RestauranteView.Resumo.class,RestauranteView.ApenasNome.class })
    private String nome;
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;
    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel Cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoModel endereco;
}
