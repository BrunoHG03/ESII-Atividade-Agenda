package com.agenda.controller;

import com.agenda.model.Atendimento;
import com.agenda.model.Receita;
import com.agenda.repository.AtendimentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "*")
public class AtendimentoController {

    private final AtendimentoRepository repository;

    public AtendimentoController(AtendimentoRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Atendimento> criar(@Valid @RequestBody Atendimento profissionalSaude) {
        Atendimento salvo = repository.save(profissionalSaude);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ TUDO
    @GetMapping
    public ResponseEntity<List<Atendimento>> listar() {
        List<Atendimento> atendimentos = repository.findAllByOrderByNomeAsc();
        return ResponseEntity.ok(atendimentos);
    }

    // READ POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // READ POR RECEITA
    @GetMapping("/receita/{receita}")
    public ResponseEntity<List<Atendimento>> listarPorReceita(@PathVariable Receita receita) {
        List<Atendimento> atendimentos = repository.findByReceitaOrderByNomeAsc(receita);
        return ResponseEntity.ok(atendimentos);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Atendimento dados) {
        return repository.findById(id)
                .map(atendimento -> {
                    atendimento.setTitulo(dados.getTitulo());
                    atendimento.setData(dados.getData());
                    atendimento.setHorario(dados.getHorario());
                    atendimento.setLinkVideo(dados.getLinkVideo());
                    atendimento.setReceita(dados.getReceita());
                    return ResponseEntity.ok(repository.save(atendimento));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(atendimento -> {
                    repository.delete(atendimento);
                    return ResponseEntity.ok(Map.of("mensagem", "Atendimento removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}