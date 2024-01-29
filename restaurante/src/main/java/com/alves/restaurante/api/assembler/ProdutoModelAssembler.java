package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.FormaPagamentoModel;
import com.alves.restaurante.api.model.ProdutoModel;
import com.alves.restaurante.domain.model.FormaPagamento;
import com.alves.restaurante.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel getProdutoModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

    public List<ProdutoModel> getListProdutoModel(
            List<Produto> produtos) {

        return produtos.stream()
                .map(produto -> getProdutoModel(produto))
                .collect(Collectors.toList());
    }
}
