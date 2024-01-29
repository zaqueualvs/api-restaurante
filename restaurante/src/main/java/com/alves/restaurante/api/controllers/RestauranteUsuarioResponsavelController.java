package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.FormaPagamentoModelAssembler;
import com.alves.restaurante.api.assembler.UsuarioModelAssembler;
import com.alves.restaurante.api.model.FormaPagamentoModel;
import com.alves.restaurante.api.model.UsuarioModel;
import com.alves.restaurante.domain.model.Restaurante;
import com.alves.restaurante.domain.services.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return usuarioModelAssembler.getListUsuarioModel(restaurante.getUsuariosResponsaveis());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId,
                            @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId,
                         @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);
    }

}

