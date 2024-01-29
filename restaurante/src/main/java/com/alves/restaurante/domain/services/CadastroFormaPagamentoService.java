package com.alves.restaurante.domain.services;

import com.alves.restaurante.domain.exceptions.EntidadeEmUsoException;
import com.alves.restaurante.domain.exceptions.EstadoNaoEncontradoException;
import com.alves.restaurante.domain.exceptions.FormaPagamentoNaoEncontradaException;
import com.alves.restaurante.domain.model.FormaPagamento;
import com.alves.restaurante.domain.repositories.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroFormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma pagamento de código %d não pode ser removido, pois está em uso!";

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id)
            );
        }
    }

    public FormaPagamento buscarOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }
}
