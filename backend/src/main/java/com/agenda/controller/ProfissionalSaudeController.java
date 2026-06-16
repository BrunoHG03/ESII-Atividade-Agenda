package com.agenda.controller;

import com.agenda.model.ProfissionalSaude;
import com.agenda.model.Categoria;
import com.agenda.repository.ProfissionalSaudeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeRepository repository;

    public ProfissionalSaudeController(ProfissionalSaudeRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ProfissionalSaude> criar(@Valid @RequestBody ProfissionalSaude profissionalSaude) {
        ProfissionalSaude salvo = repository.save(profissionalSaude);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ TUDO
    @GetMapping
    public ResponseEntity<List<ProfissionalSaude>> listar() {
        List<ProfissionalSaude> profissionais = repository.findAllByOrderByNomeAsc();
        return ResponseEntity.ok(profissionais);
    }

    // READ POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // READ POR CATEGORIA
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProfissionalSaude>> listarPorCategoria(@PathVariable Categoria categoria) {
        List<ProfissionalSaude> profissionais = repository.findByCategoriaOrderByNomeAsc(categoria);
        return ResponseEntity.ok(profissionais);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody ProfissionalSaude dados) {
        return repository.findById(id)
                .map(profissionalSaude -> {
                    profissionalSaude.setNome(dados.getNome());
                    profissionalSaude.setTelefone(dados.getTelefone());
                    profissionalSaude.setEndereco(dados.getEndereco());
                    profissionalSaude.setCategoria(dados.getCategoria());
                    return ResponseEntity.ok(repository.save(profissionalSaude));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id)
                .map(profissionalSaude -> {
                    repository.delete(profissionalSaude);
                    return ResponseEntity.ok(Map.of("mensagem", "Profissional removido com sucesso"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}