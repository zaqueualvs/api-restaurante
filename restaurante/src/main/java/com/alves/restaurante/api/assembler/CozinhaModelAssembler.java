package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.CozinhaModel;
import com.alves.restaurante.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModel getCozinhaModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

    public List<CozinhaModel> getListCozinhaModel(List<Cozinha> cozinhas) {

        return cozinhas.stream()
                .map(cozinha -> getCozinhaModel(cozinha))
                .collect(Collectors.toList());
    }
}
