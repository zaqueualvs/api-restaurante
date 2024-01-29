package com.alves.restaurante.domain.services;

import com.alves.restaurante.domain.exceptions.CidadeNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.EntidadeEmUsoException;
import com.alves.restaurante.domain.exceptions.EntidadeNaoEncontradaException;
import com.alves.restaurante.domain.model.Cidade;
import com.alves.restaurante.domain.model.Cozinha;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.repositories.CidadeRepository;
import com.alves.restaurante.domain.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;
    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removido, pois está em uso!";

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id)
            );
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }
}
