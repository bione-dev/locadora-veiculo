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
        try {
            veiculoRepository.persist(veiculo); // Persistir o veículo no banco
            return veiculo;
        } catch (Exception e) {
            // Lançar uma exceção mais específica com uma mensagem clara
            throw new RuntimeException("Erro ao salvar o veículo: " + e.getMessage(), e);
        }
    }

    public Veiculo buscarVeiculoPorId(Long id) {
        try {
            return veiculoRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar o veículo com ID " + id + ": " + e.getMessage(), e);
        }
    }

    public List<Veiculo> listarTodos() {
        try {
            return veiculoRepository.listAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar veículos: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> listarTodosPaginados(int pagina, int tamanho, String ordenarPor, String direcao) {
        try {
            String query = "SELECT v FROM Veiculo v ORDER BY v." + ordenarPor + " " + direcao;
            return veiculoRepository.getEntityManager()
                    .createQuery(query, Veiculo.class)
                    .setFirstResult(pagina * tamanho)
                    .setMaxResults(tamanho)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar veículos paginados: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> listarVeiculosPorCategoria(String categoriaNome) {
        try {
            return veiculoRepository.findByCategoriaNome(categoriaNome);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar veículos da categoria " + categoriaNome + ": " + e.getMessage(), e);
        }
    }
}
