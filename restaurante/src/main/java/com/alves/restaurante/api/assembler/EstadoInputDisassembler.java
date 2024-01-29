package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.input.CozinhaInput;
import com.alves.restaurante.api.model.input.EstadoInput;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
