package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.GrupoInputDisassembler;
import com.alves.restaurante.api.assembler.GrupoModelAssembler;
import com.alves.restaurante.api.model.GrupoModel;
import com.alves.restaurante.domain.model.Usuario;
import com.alves.restaurante.domain.services.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupo")
public class UsuarioGrupoController {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public List<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return grupoModelAssembler.getListGrupoModel(usuario.getUsuarioGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
    }

}

