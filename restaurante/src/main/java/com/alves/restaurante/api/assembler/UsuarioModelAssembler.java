package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.CidadeModel;
import com.alves.restaurante.api.model.UsuarioModel;
import com.alves.restaurante.domain.model.Cidade;
import com.alves.restaurante.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel getUsuarioModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> getListUsuarioModel(Collection<Usuario> usuarios) {

        return usuarios.stream()
                .map(usuario -> getUsuarioModel(usuario))
                .collect(Collectors.toList());
    }
}
