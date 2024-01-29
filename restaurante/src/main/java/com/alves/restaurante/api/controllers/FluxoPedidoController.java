package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.PedidoInputDisassembler;
import com.alves.restaurante.api.assembler.PedidoModelAssembler;
import com.alves.restaurante.api.assembler.PedidoResumoModelAssembler;
import com.alves.restaurante.api.model.PedidoModel;
import com.alves.restaurante.api.model.PedidoResumoModel;
import com.alves.restaurante.api.model.input.PedidoInput;
import com.alves.restaurante.domain.exceptions.EntidadeNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.NegocioException;
import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.model.Usuario;
import com.alves.restaurante.domain.repositories.PedidoRepository;
import com.alves.restaurante.domain.services.CadastroPedidoService;
import com.alves.restaurante.domain.services.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos/{codigoPedido}")
public class FluxoPedidoController {
    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.entregar(codigoPedido);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID codigoPedido) {
        fluxoPedidoService.cancelar(codigoPedido);
    }

}

