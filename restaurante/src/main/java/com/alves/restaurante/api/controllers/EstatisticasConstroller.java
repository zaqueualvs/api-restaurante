package com.alves.restaurante.api.controllers;

import com.alves.restaurante.domain.filter.VendaDiariaFilter;
import com.alves.restaurante.domain.model.dto.VendaDiaria;
import com.alves.restaurante.domain.services.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasConstroller {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filter, timeOffset);
    }
}
