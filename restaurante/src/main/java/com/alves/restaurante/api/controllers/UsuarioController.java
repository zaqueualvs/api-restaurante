package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.UsuarioInputDisassembler;
import com.alves.restaurante.api.assembler.UsuarioModelAssembler;
import com.alves.restaurante.api.model.UsuarioModel;
import com.alves.restaurante.api.model.input.SenhaInput;
import com.alves.restaurante.api.model.input.UsuarioComSenhaInput;
import com.alves.restaurante.api.model.input.UsuarioInput;
import com.alves.restaurante.domain.model.Usuario;
import com.alves.restaurante.domain.repositories.UsuarioRepository;
import com.alves.restaurante.domain.services.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioModel> listar() {
        return usuarioModelAssembler.getListUsuarioModel(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioModel buscar(@PathVariable Long id) {
        return usuarioModelAssembler.getUsuarioModel(cadastroUsuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    public UsuarioModel inserir(@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioComSenhaInput);
        return usuarioModelAssembler.getUsuarioModel(cadastroUsuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioModel atualizar(@PathVariable Long id,
                                  @Valid @RequestBody UsuarioInput usuarioInput) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(id);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);
        return usuarioModelAssembler.getUsuarioModel(cadastroUsuarioService.salvar(usuario));
    }
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        cadastroUsuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroUsuarioService.excluir(id);
    }

}

