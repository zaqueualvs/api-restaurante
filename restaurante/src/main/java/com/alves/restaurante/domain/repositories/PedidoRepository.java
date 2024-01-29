package com.alves.restaurante.domain.repositories;

import com.alves.restaurante.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("FROM Pedido p JOIN FETCH p.cliente JOIN FETCH  p.restaurante r JOIN FETCH  r.cozinha")
    List<Pedido> findAll();

    Optional<Pedido> findByCodigo(UUID codigo);


}
