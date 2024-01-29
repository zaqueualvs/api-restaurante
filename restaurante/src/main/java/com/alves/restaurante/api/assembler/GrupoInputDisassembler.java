package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.input.EstadoInput;
import com.alves.restaurante.api.model.input.GrupoInput;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
