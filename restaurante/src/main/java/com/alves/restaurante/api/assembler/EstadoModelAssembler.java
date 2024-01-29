package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.EstadoModel;
import com.alves.restaurante.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel getEstadoModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> getListEstadoModel(List<Estado> estados) {

        return estados.stream()
                .map(estado -> getEstadoModel(estado))
                .collect(Collectors.toList());
    }
}
