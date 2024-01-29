package com.alves.restaurante.core.modelMapper;

import com.alves.restaurante.api.model.EnderecoModel;
import com.alves.restaurante.api.model.input.ItemPedidoInput;
import com.alves.restaurante.domain.model.Endereco;
import com.alves.restaurante.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper getModelMapper() {

        var modelMapper = new ModelMapper();
        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoModel.class
        );

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                enderedoSrc -> enderedoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value)
        );

        return modelMapper;
    }
}
