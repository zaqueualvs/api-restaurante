package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.input.CidadeInput;
import com.alves.restaurante.api.model.input.RestauranteInput;
import com.alves.restaurante.domain.model.Cidade;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade){
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }
}
