package com.alves.restaurante.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @OneToOne
    private Produto produto;
    @ManyToOne
    private Pedido pedido;

    public void calculaValorTotal() {
        BigDecimal valorUnitario = getPrecoUnitario();
        Integer quantidade = getQuantidade();
        if (precoUnitario == null) {
            precoUnitario = BigDecimal.ZERO;
        }
        if (quantidade == null) {
            quantidade = 0;
        }
        setPrecoTotal(valorUnitario.multiply(new BigDecimal(quantidade)));
    }
}
