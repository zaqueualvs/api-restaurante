package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.EstadoModel;
import com.alves.restaurante.api.model.GrupoModel;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel getGrupoModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public List<GrupoModel> getListGrupoModel(List<Grupo> grupos) {

        return grupos.stream()
                .map(grupo -> getGrupoModel(grupo))
                .collect(Collectors.toList());
    }
}
