package com.alves.restaurante.domain.model;

import com.alves.restaurante.core.validation.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private Boolean ativo = Boolean.TRUE;
    private Boolean aberto = Boolean.TRUE;

    @ManyToOne
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    private OffsetDateTime dataCadastro;
    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id "))
    private Set<FormaPagamento> formasPagamentos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id "))
    private Set<Usuario> usuariosResponsaveis = new HashSet<>();


    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().add(formaPagamento);
    }

    public Boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().contains(formaPagamento);
    }

    public boolean adicionarUsuarioResponsavel(Usuario usuario) {
        return getUsuariosResponsaveis().add(usuario);
    }

    public boolean removerUsuarioResponsavel(Usuario usuario) {
        return getUsuariosResponsaveis().remove(usuario);
    }

}
