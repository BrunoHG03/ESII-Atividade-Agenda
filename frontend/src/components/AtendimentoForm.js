import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { atendimentoService } from '../services/api';

function AtendimentoForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [atendimento, setAtendimento] = useState({
    titulo: '', data: '', horario: '', linkVideo: '', receita: ''
  });

  useEffect(() => {
    if (id) {
      atendimentoService.buscar(id).then(res => setAtendimento(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await atendimentoService.atualizar(id, atendimento);
      } else {
        await atendimentoService.criar(atendimento);
      }
      navigate('/atendimentos');
    } catch (error) {
      console.error('Erro ao salvar atendimento:', error);
    }
  };

  return (
    <div>
      <h2>{id ? 'Editar Atendimento' : 'Novo Atendimento'}</h2>
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label>Título *</label>
          <input type="text" value={atendimento.titulo} required
            onChange={e => setAtendimento({...atendimento, titulo: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Data</label>
          <input type="text" value={atendimento.data}
            onChange={e => setAtendimento({...atendimento, data: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Horário</label>
          <input type="text" value={atendimento.horario}
            onChange={e => setAtendimento({...atendimento, horario: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Link do Vídeo</label>
          <input type="text" value={atendimento.linkVideo}
            onChange={e => setAtendimento({...atendimento, linkVideo: e.target.value})} />
        </div>
        <div className="form-group">
          <label>Receita</label>
          <input type="text" value={atendimento.receita}
            onChange={e => setAtendimento({...atendimento, receita: e.target.value})} />
        </div>
        <button type="submit" className="btn btn-primary">Salvar</button>
        <button type="button" className="btn" onClick={() => navigate('/atendimentos')}>Cancelar</button>
      </form>
    </div>
  );
}

export default AtendimentoForm;