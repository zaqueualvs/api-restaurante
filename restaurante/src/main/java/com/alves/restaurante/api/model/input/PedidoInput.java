package com.alves.restaurante.api.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PedidoInput {
    private RestauranteIdInput restaurante;
    private EnderecoInput enderecoEntrega;
    private FormaPagamentoInput formaPagamento;
    private List<ItemPedidoInput> itemPedidos = new ArrayList<>();
}
