package com.alves.restaurante.api.model;

import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.model.Produto;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel {
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
    private String produtoNome;
}
