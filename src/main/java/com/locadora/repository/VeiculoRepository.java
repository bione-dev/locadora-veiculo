package com.locadora.repository;

import com.locadora.model.Veiculo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VeiculoRepository implements PanacheRepository<Veiculo> {

    public List<Veiculo> findByCategoriaNome(String categoriaNome) {
        return find("categoria.nome", categoriaNome).list();
    }
}
