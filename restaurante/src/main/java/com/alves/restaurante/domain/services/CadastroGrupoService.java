package com.alves.restaurante.domain.services;

import com.alves.restaurante.domain.exceptions.EntidadeEmUsoException;
import com.alves.restaurante.domain.exceptions.EstadoNaoEncontradoException;
import com.alves.restaurante.domain.exceptions.GrupoNaoEncontradaException;
import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.model.Grupo;
import com.alves.restaurante.domain.model.Permissao;
import com.alves.restaurante.domain.repositories.EstadoRepository;
import com.alves.restaurante.domain.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso!";

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id)
            );
        }
    }
    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId){
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
        grupo.adicionarPermissao(permissao);
    }
    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId){
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
        grupo.removerPermissao(permissao);
    }

    public Grupo buscarOuFalhar(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradaException(id));
    }
}
