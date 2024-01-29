package com.alves.restaurante.domain.repositories;

import com.alves.restaurante.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
    List<Cozinha> findAllByNomeContaining(String nome);

    Optional<Cozinha> findByNome(String nome);
}
