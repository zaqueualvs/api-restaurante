package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.GrupoInputDisassembler;
import com.alves.restaurante.api.assembler.GrupoModelAssembler;
import com.alves.restaurante.api.model.GrupoModel;
import com.alves.restaurante.api.model.input.GrupoInput;
import com.alves.restaurante.domain.model.Grupo;
import com.alves.restaurante.domain.repositories.GrupoRepository;
import com.alves.restaurante.domain.services.CadastroGrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @Autowired
    private GrupoModelAssembler grupoModelAssembler;
    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {
        return grupoModelAssembler.getListGrupoModel(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoModel buscar(@PathVariable Long id) {
        return grupoModelAssembler.getGrupoModel(cadastroGrupoService.buscarOuFalhar(id));
    }

    @PostMapping
    public GrupoModel inserir(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        return grupoModelAssembler.getGrupoModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoModel atualizar(@PathVariable Long id,
                                @Valid @RequestBody GrupoInput grupoInput) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(id);
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupo);
        return grupoModelAssembler.getGrupoModel(cadastroGrupoService.salvar(grupo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroGrupoService.excluir(id);
    }

}

