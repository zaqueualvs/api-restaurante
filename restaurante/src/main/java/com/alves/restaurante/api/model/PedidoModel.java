package com.alves.restaurante.api.model;

import com.alves.restaurante.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class PedidoModel {
    private UUID codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private UsuarioModel cliente;
    private RestauranteResumidoModel restaurante;
    private EnderecoModel enderecoEntrega;
    private FormaPagamentoModel formaPagamento;
    private List<ItemPedidoModel> itemPedidos = new ArrayList<>();
}
