package com.alves.restaurante.infrastructure.repository.spec;

import com.alves.restaurante.domain.model.Pedido;
import com.alves.restaurante.domain.filter.PedidoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class PedidoSpecs {
    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");
            var predicates = new ArrayList<Predicate>();

            if (filtro.getClientId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClientId()));
            }
            if (filtro.getRestautanteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestautanteId()));
            }
            if (filtro.getDaCriacaoFim() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDaCriacaoInicio()));
            }
            if (filtro.getDaCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacaoFim"), filtro.getDaCriacaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }

}
