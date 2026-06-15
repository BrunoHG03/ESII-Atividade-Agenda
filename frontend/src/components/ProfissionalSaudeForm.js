import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { profissionalSaudeService } from '../services/api';

function ContatoForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [contato, setProfissional] = useState({
    nome: '', telefone: '', endereco: '', categoria: ''
  });

  useEffect(() => {
    if (id) {
      profissionalSaudeService.buscar(id).then(res => setProfissional(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await profissionalSaudeService.atualizar(id, profissionalSaude);
      } else {
        await profissionalSaudeService.criar(profissionalSaude);
      }
      navigate('/profissionais');
    } catch (error) {
      console.error('Erro ao salvar profissional:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Profissional de Saúde' : 'Novo Profissional de Saúde'}</h2>
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label>Nome *</label>
          <input type="text" value={profissionalSaude.nome} required
            onChange={e => setProfissional({...profissionalSaude, nome: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Telefone</label>
          <input type="text" value={profissionalSaude.telefone}
            onChange={e => setProfissional({...profissionalSaude, telefone: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Endereço</label>
          <input type="text" value={profissionalSaude.endereco}
            onChange={e => setProfissional({...profissionalSaude, endereco: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Categoria</label>
          <input type="text" value={profissionalSaude.categoria}
            onChange={e => setProfissional({...profissionalSaude, categoria: e.target.value})} />
        </div>
        <button type="submit" className="btn btn-primary">Salvar</button>
        <button type="button" className="btn" onClick={() => navigate('/profissionais')}>Cancelar</button>
      </form>
    </div>
  );
}

export default ContatoForm;