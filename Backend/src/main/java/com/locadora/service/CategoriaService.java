package com.locadora.service;

import com.locadora.model.Categoria;
import com.locadora.repository.CategoriaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    /**
     * Lista todas as categorias cadastradas.
     * @return Lista de categorias.
     */
    public List<Categoria> listarCategorias() {
        try {
            List<Categoria> categorias = categoriaRepository.listAll();
            if (categorias.isEmpty()) {
                throw new RuntimeException("Nenhuma categoria cadastrada.");
            }
            return categorias;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar categorias: " + e.getMessage(), e);
        }
    }

    /**
     * Busca uma categoria pelo ID.
     * @param id ID da categoria.
     * @return Categoria encontrada.
     */
    public Categoria buscarPorId(Long id) {
        try {
            Categoria categoria = categoriaRepository.findById(id);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com o ID: " + id);
            }
            return categoria;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a categoria com ID " + id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Adiciona uma nova categoria.
     * @param categoria Categoria a ser adicionada.
     * @return Categoria criada.
     */
    @Transactional
    public Categoria adicionarCategoria(Categoria categoria) {
        try {
            // Verificar duplicidade de categoria pelo nome
            if (categoriaRepository.find("nome", categoria.getNome()).firstResult() != null) {
                throw new RuntimeException("Categoria já existe com o nome: " + categoria.getNome());
            }
            categoriaRepository.persist(categoria); // Persistir a nova categoria no banco
            return categoria; // Retornar a categoria criada
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar a categoria: " + e.getMessage(), e);
        }
    }

    /**
     * Remove uma categoria pelo ID.
     * @param id ID da categoria a ser removida.
     */
    @Transactional
    public void removerCategoria(Long id) {
        try {
            Categoria categoria = buscarPorId(id); // Buscar a categoria para remover
            if (categoria == null) {
                throw new RuntimeException("Categoria com ID " + id + " não encontrada.");
            }
            categoriaRepository.delete(categoria); // Remover a categoria
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover a categoria com ID " + id + ": " + e.getMessage(), e);
        }
    }
}
