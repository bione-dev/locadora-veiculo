package com.locadora.service;

import com.locadora.model.Veiculo;
import com.locadora.repository.VeiculoRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class VeiculoService {

    @Inject
    VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo salvarVeiculo(Veiculo veiculo) {
        veiculoRepository.persist(veiculo);
        return veiculo;
    }

    public Veiculo buscarVeiculoPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.listAll();
    }
}
