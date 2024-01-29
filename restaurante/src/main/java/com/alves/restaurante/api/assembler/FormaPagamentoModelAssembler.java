package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.FormaPagamentoModel;
import com.alves.restaurante.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel getFormaPagamentoModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> getListFormaPagamentoModel(
            Collection<FormaPagamento> formaPagamentos) {

        return formaPagamentos.stream()
                .map(formaPagamento -> getFormaPagamentoModel(formaPagamento))
                .collect(Collectors.toList());
    }
}
