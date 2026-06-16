import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProfissionalSaudeList from './components/ProfissionalSaudeList';
import ProfissionalSaudeForm from './components/ProfissionalSaudeForm';
import AtendimentoList from './components/AtendimentoList';
import AtendimentoForm from './components/AtendimentoForm';
import ExameList from './components/ExameList';
import ExameForm from './components/ExameForm';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <h1>📅 Agenda Web</h1>
          <div className="nav-links">
            <Link to="/profissionais">Profissionais de Saúde</Link>
            <Link to="/atendimentos">Atendimentos</Link>
            <Link to="/exames">Exames</Link>
          </div>
        </nav>

        <main className="container">
          <Routes>
            <Route path="/" element={<ContatoList />} />
            <Route path="/profissionais" element={<ContatoList />} />
            <Route path="/profissionais/novo" element={<ContatoForm />} />
            <Route path="/profissionais/editar/:id" element={<ContatoForm />} />
            <Route path="/atendimentos" element={<CompromissoList />} />
            <Route path="/atendimentos/novo" element={<CompromissoForm />} />
            <Route path="/atendimentos/editar/:id" element={<CompromissoForm />} />
            <Route path="/exames" element={<CompromissoList />} />
            <Route path="/exames/novo" element={<CompromissoForm />} />
            <Route path="/exames/editar/:id" element={<CompromissoForm />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;