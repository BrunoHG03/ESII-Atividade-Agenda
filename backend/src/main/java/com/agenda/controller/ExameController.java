package com.agenda.controller;

import com.agenda.model.Exame;
import com.agenda.repository.ExameRepository;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exames")
@CrossOrigin(origins = "*")
public class ExameController {

    private final ExameRepository repository;

    public ExameController(ExameRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Exame> criar(@Valid @RequestBody Exame exame) {
        Exame salvo = repository.save(exame);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ TUDO
    @GetMapping
    public ResponseEntity<List<Exame>> listar() {
        List<Exame> exames = repository.findAllByOrderByDescricaoAsc();
        return ResponseEntity.ok(exames);
    }

    // READ POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Exame dados) {
        return repository.findById(id)
                .map(exame -> {
                    exame.setDescricao(dados.getDescricao());
                    exame.setPatologia(dados.getPatologia());
                    return ResponseEntity.ok(repository.save(exame));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(exame -> {
                    repository.delete(exame);
                    return ResponseEntity.ok(Map.of("mensagem", "Exame removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}