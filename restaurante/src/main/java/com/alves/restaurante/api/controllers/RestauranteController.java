package com.alves.restaurante.api.controllers;

import com.alves.restaurante.api.assembler.RestauranteInputDisassembler;
import com.alves.restaurante.api.assembler.RestauranteModelAssembler;
import com.alves.restaurante.api.model.CozinhaModel;
import com.alves.restaurante.api.model.RestauranteModel;
import com.alves.restaurante.api.model.input.CozinhaIdInput;
import com.alves.restaurante.api.model.input.RestauranteInput;
import com.alves.restaurante.api.model.view.RestauranteView;
import com.alves.restaurante.core.validation.ValidacaoException;
import com.alves.restaurante.domain.exceptions.CidadeNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.CozinhaNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.NegocioException;
import com.alves.restaurante.domain.exceptions.RestauranteNaoEncontradoException;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Restaurante;
import com.alves.restaurante.domain.repositories.RestauranteRepository;
import com.alves.restaurante.domain.services.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private SmartValidator smartValidator;
    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;
    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @GetMapping
    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        List<RestauranteModel> restauranteModels = restauranteModelAssembler
                .getListRestauranteModel(restaurantes);
        MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restauranteModels);

        if ("apenas-nome".equals(projecao)) {
            restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if ("resumo".equals(projecao)) {
            restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
        }

        return restauranteWrapper;
    }

    @GetMapping("/{id}")
    public RestauranteModel buscar(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
        return restauranteModelAssembler.getRestauranteModel(restaurante);
    }

    @PostMapping
    public RestauranteModel adicionar(@RequestBody
                                      @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteModelAssembler.getRestauranteModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(@PathVariable Long id,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {

        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
        try {
            return restauranteModelAssembler.getRestauranteModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        cadastroRestauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        cadastroRestauranteService.abrir(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> ids) {

        try {
            cadastroRestauranteService.ativar(ids);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> ids) {
        try {
            cadastroRestauranteService.inativar(ids);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id) {
        cadastroRestauranteService.fechar(id);
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id) {
        cadastroRestauranteService.abrir(id);
    }

    /*@PatchMapping("/{id}")
    public RestauranteModel atualizarParcial(@PathVariable Long id,
                                             @RequestBody Map<String, Object> campos,
                                             HttpServletRequest httpServletRequest) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
        merge(campos, restauranteAtual, httpServletRequest);

        validate(restauranteAtual, "restaurante");
        return atualizar(id, restauranteAtual);
    }

    private void merge(Map<String, Object> camposOrigem,
                       Restaurante restauranteDestino,
                       HttpServletRequest httpServletRequest) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
        try {


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
            camposOrigem.forEach((chave, valor) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, chave);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCausa = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCausa, servletServerHttpRequest);
        }

    }

    private void validate(Restaurante restaurante, String objectName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        smartValidator.validate(restaurante, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidacaoException(bindingResult);
        }
    }*/

}

