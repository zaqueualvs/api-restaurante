package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.PermissaoModelAssembler;
import com.alves.restaurante.api.assembler.PermissaoInputDisassembler;
import com.alves.restaurante.api.model.PermissaoModel;
import com.alves.restaurante.domain.model.Grupo;
import com.alves.restaurante.domain.services.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;
    @Autowired
    private PermissaoInputDisassembler permissaoInputDisassembler;

    @GetMapping
    public List<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        return permissaoModelAssembler.getListPermissaoModel(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId,
                                     @PathVariable Long permissaoId){
        cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

}

