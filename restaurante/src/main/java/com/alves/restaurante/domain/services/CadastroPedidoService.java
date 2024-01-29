package com.alves.restaurante.domain.services;

import com.alves.restaurante.core.validation.Groups;
import com.alves.restaurante.domain.exceptions.EntidadeEmUsoException;
import com.alves.restaurante.domain.exceptions.NegocioException;
import com.alves.restaurante.domain.exceptions.PedidoEncontradoException;
import com.alves.restaurante.domain.model.*;
import com.alves.restaurante.domain.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    private static final String MSG_PEDIDO_EM_USO = "Pedido de código %d não pode ser removido, pois está em uso!";

    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calculaValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido) {
        pedido.getItemPedidos().forEach(item -> {
            Produto produto = cadastroProdutoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    private void validarPedido(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar((pedido.getFormaPagamento().getId()));
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

    }


    public Pedido buscarOuFalhar(UUID codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoEncontradoException(codigo));
    }
}
