package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.ProdutoInputDisassembler;
import com.alves.restaurante.api.assembler.ProdutoModelAssembler;
import com.alves.restaurante.api.model.ProdutoModel;
import com.alves.restaurante.api.model.input.ProdutoInput;
import com.alves.restaurante.domain.model.Produto;
import com.alves.restaurante.domain.model.Restaurante;
import com.alves.restaurante.domain.repositories.ProdutoRepository;
import com.alves.restaurante.domain.services.CadastroProdutoService;
import com.alves.restaurante.domain.services.CadastroRestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;
    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId,
                                     @RequestParam(required = false) Boolean incluirInativos) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos;
        if (incluirInativos) {
            produtos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findByAtivoRestaurante(restaurante);
        }
        return produtoModelAssembler.getListProdutoModel(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoModelAssembler.getProdutoModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId,
                                  @Valid @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoModelAssembler.getProdutoModel(cadastroProdutoService.salvar(produto));
    }

    @PostMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId,
                                  @PathVariable Long produtoId,
                                  @Valid @RequestBody ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        return produtoModelAssembler.getProdutoModel(cadastroProdutoService.salvar(produto));
    }

}

