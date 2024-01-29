package com.alves.restaurante.api.model;

import com.alves.restaurante.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PedidoResumoModel {

    private UUID codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private OffsetDateTime dataCriacao;
    private UsuarioModel cliente;
    private RestauranteResumidoModel restaurante;
}
