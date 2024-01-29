package com.alves.restaurante.domain.services;

import com.alves.restaurante.domain.exceptions.EntidadeEmUsoException;
import com.alves.restaurante.domain.exceptions.FormaPagamentoNaoEncontradaException;
import com.alves.restaurante.domain.exceptions.PermissaoEncontradaException;
import com.alves.restaurante.domain.model.FormaPagamento;
import com.alves.restaurante.domain.model.Permissao;
import com.alves.restaurante.domain.repositories.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    private static final String MSG_PERMISSAO_EM_USO = "Permissao de código %d não pode ser removido, pois está em uso!";

    public Permissao salvar(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            permissaoRepository.deleteById(id);
            permissaoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PERMISSAO_EM_USO, id)
            );
        }
    }

    public Permissao buscarOuFalhar(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoEncontradaException(id));
    }
}
