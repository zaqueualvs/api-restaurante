package com.alves.restaurante.domain.services;

import com.alves.restaurante.domain.filter.VendaDiariaFilter;
import com.alves.restaurante.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
