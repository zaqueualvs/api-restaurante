package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.PedidoResumoModel;
import com.alves.restaurante.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoModel getPedidoModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> getListPedidoModel(Collection<Pedido> pedidos) {

        return pedidos.stream()
                .map(pedido -> getPedidoModel(pedido))
                .collect(Collectors.toList());
    }
}
