package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.FormaPagamentoInputDisassembler;
import com.alves.restaurante.api.assembler.FormaPagamentoModelAssembler;
import com.alves.restaurante.api.model.FormaPagamentoModel;
import com.alves.restaurante.api.model.input.FormaPagamentoInput;
import com.alves.restaurante.domain.model.FormaPagamento;
import com.alves.restaurante.domain.repositories.FormaPagamentoRepository;
import com.alves.restaurante.domain.services.CadastroFormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        return formaPagamentoModelAssembler
                .getListFormaPagamentoModel(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id) {
        return formaPagamentoModelAssembler.getFormaPagamentoModel(
                cadastroFormaPagamentoService.buscarOuFalhar(id)
        );
    }

    @PostMapping
    public FormaPagamentoModel inserir(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        return formaPagamentoModelAssembler.getFormaPagamentoModel(
                cadastroFormaPagamentoService.salvar(formaPagamento)
        );
    }

    @PutMapping("/{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id,
                                         @Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

        return formaPagamentoModelAssembler.getFormaPagamentoModel(
                cadastroFormaPagamentoService.salvar(formaPagamento)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }

}

