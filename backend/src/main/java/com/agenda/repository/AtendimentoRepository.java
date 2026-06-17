package com.agenda.repository;

import com.agenda.model.Atendimento;
import com.agenda.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findAllByOrderByTituloAsc();

    List<Atendimento> findByTituloContainingIgnoreCase(String titulo);

    List<Atendimento> findByReceitaOrderByTituloAsc(Receita receita);
}