package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.CidadeModel;
import com.alves.restaurante.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModel getCidadeModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }
    public List<CidadeModel> getListCidadeModel(List<Cidade> cidades) {

        return cidades.stream()
                .map(cidade -> getCidadeModel(cidade))
                .collect(Collectors.toList());
    }
}
