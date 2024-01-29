package com.alves.restaurante.api.assembler;

import com.alves.restaurante.api.model.input.CidadeInput;
import com.alves.restaurante.api.model.input.CozinhaInput;
import com.alves.restaurante.domain.model.Cidade;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    public Cozinha toDomainObject(CozinhaInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha){
        modelMapper.map(cozinhaInput, cozinha);
    }
}
