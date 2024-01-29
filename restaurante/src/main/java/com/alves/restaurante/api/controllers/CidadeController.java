package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.CidadeInputDisassembler;
import com.alves.restaurante.api.assembler.CidadeModelAssembler;
import com.alves.restaurante.api.model.CidadeModel;
import com.alves.restaurante.api.model.input.CidadeInput;
import com.alves.restaurante.domain.exceptions.EstadoNaoEncontradoException;
import com.alves.restaurante.domain.exceptions.NegocioException;
import com.alves.restaurante.domain.model.Cidade;
import com.alves.restaurante.domain.repositories.CidadeRepository;
import com.alves.restaurante.domain.services.CadastroCidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;
    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {
        return cidadeModelAssembler
                .getListCidadeModel(
                        cidadeRepository
                                .findAll()
                );
    }

    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id) {
        return cidadeModelAssembler
                .getCidadeModel(
                        cadastroCidadeService
                                .buscarOuFalhar(id)
                );
    }

    @PostMapping
    public CidadeModel inserir(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            return cidadeModelAssembler
                    .getCidadeModel(
                            cadastroCidadeService
                                    .salvar(cidade)
                    );
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CidadeModel atualizar(@PathVariable Long id,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
        try {
            return cidadeModelAssembler
                    .getCidadeModel(
                            cadastroCidadeService
                                    .salvar(cidadeAtual)
                    );
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroCidadeService.excluir(id);
    }
}

