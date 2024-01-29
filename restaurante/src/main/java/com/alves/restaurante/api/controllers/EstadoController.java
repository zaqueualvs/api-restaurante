package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.EstadoInputDisassembler;
import com.alves.restaurante.api.assembler.EstadoModelAssembler;
import com.alves.restaurante.api.model.EstadoModel;
import com.alves.restaurante.api.model.input.EstadoInput;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.repositories.EstadoRepository;
import com.alves.restaurante.domain.services.CadastroEstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;
    @Autowired
    private EstadoModelAssembler estadoModelAssembler;
    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return estadoModelAssembler.getListEstadoModel(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id) {
        return estadoModelAssembler.getEstadoModel(cadastroEstadoService.buscarOuFalhar(id));
    }

    @PostMapping
    public EstadoModel inserir(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        return estadoModelAssembler.getEstadoModel(cadastroEstadoService.salvar(estado));
    }

    @PutMapping("/{id}")
    public EstadoModel atualizar(@PathVariable Long id,
                                 @Valid @RequestBody EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(id);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
        return estadoModelAssembler.getEstadoModel(cadastroEstadoService.salvar(estadoAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroEstadoService.excluir(id);
    }

}

