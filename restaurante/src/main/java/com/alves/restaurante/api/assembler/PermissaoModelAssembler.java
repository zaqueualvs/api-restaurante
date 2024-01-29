package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.PermissaoModel;
import com.alves.restaurante.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel getPermissaoModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> getListPermissaoModel(Collection<Permissao> permissoes) {

        return permissoes.stream()
                .map(permissao -> getPermissaoModel(permissao))
                .collect(Collectors.toList());
    }
}
