package com.alves.restaurante.api.model.input;

import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoInput {
    private Integer quantidade;
    private String observacao;
    private Long produtoId;
}
