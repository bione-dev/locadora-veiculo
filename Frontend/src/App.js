import './App.css';
import React, { useEffect, useState } from 'react';

function App() {
  const [veiculosPorCategoria, setVeiculosPorCategoria] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/veiculos') // Altere a URL se necessário
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log('Dados recebidos:', data);

        // Organizar os veículos por categoria
        const agrupadosPorCategoria = data.reduce((acc, veiculo) => {
          const { nome: categoriaNome } = veiculo.categoria;

          if (!acc[categoriaNome]) {
            acc[categoriaNome] = [];
          }
          acc[categoriaNome].push(veiculo);
          return acc;
        }, {});

        setVeiculosPorCategoria(agrupadosPorCategoria);
      })
      .catch((error) => {
        console.error('Erro ao buscar veículos:', error);
        setError(error.message);
      });
  }, []);

  return (
    <div>
      <h1>Lista de Veículos por Categoria</h1>
      {error && <p style={{ color: 'red' }}>Erro: {error}</p>}
      <div>
        {Object.keys(veiculosPorCategoria).map((categoria) => (
          <div key={categoria} style={{ marginBottom: '20px' }}>
            <h2>{categoria}</h2>
            <ul>
              {veiculosPorCategoria[categoria].map((veiculo) => (
                <li key={veiculo.id}>
                  {veiculo.modelo} (ID: {veiculo.id})
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
