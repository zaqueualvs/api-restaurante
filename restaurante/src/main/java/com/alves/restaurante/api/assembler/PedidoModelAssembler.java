package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.PedidoModel;
import com.alves.restaurante.api.model.PermissaoModel;
import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModel getPedidoModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> getListPedidoModel(Collection<Pedido> pedidos) {

        return pedidos.stream()
                .map(pedido -> getPedidoModel(pedido))
                .collect(Collectors.toList());
    }
}
