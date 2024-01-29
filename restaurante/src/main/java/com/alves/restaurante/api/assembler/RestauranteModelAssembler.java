package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.CozinhaModel;
import com.alves.restaurante.api.model.RestauranteModel;
import com.alves.restaurante.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel getRestauranteModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModel.class);
    }
    public List<RestauranteModel> getListRestauranteModel(List<Restaurante> restaurantes) {

        return restaurantes.stream()
                .map(restaurante -> getRestauranteModel(restaurante))
                .collect(Collectors.toList());
    }
}
