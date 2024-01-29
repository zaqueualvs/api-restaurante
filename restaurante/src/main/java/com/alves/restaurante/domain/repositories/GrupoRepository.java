package com.alves.restaurante.domain.repositories;

import com.alves.restaurante.domain.model.Estado;
import com.alves.restaurante.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

}
