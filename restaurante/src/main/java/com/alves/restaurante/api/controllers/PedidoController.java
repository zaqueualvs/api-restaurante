package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.*;
import com.alves.restaurante.api.model.PedidoModel;
import com.alves.restaurante.api.model.PedidoResumoModel;
import com.alves.restaurante.api.model.input.PedidoInput;
import com.alves.restaurante.domain.exceptions.EntidadeNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.NegocioException;
import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.model.Usuario;
import com.alves.restaurante.domain.repositories.PedidoRepository;
import com.alves.restaurante.domain.filter.PedidoFilter;
import com.alves.restaurante.domain.services.CadastroPedidoService;
import com.alves.restaurante.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedidoService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;
    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;
    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    /*GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResumoModel> pedidoResumoModel = pedidoResumoModelAssembler.getListPedidoModel(pedidos);
        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumoModel);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
        if(StringUtils.isNotBlank(campos)){
            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
        }
        pedidosWrapper.setFilters(filterProvider);
        return pedidosWrapper;
    }*/

    @GetMapping
    public List<PedidoResumoModel> pesquisar(PedidoFilter filter) {
        return pedidoResumoModelAssembler.getListPedidoModel(pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter)));
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            return pedidoModelAssembler.getPedidoModel(cadastroPedidoService.salvar(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }


    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable UUID codigoPedido) {
        return pedidoModelAssembler.getPedidoModel(cadastroPedidoService.buscarOuFalhar(codigoPedido));
    }


}

