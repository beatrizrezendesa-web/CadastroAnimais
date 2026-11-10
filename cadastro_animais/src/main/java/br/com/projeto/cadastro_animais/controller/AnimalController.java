package br.com.projeto.cadastro_animais.controller;


import br.com.projeto.cadastro_animais.model.Animal;
import br.com.projeto.cadastro_animais.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/animais")
public class AnimalController {
    private final AnimalService service;
    public AnimalController(AnimalService service) {
        this.service = service;
    }
    @GetMapping
    public List<Animal> listar() { return service.listar(); }
    @GetMapping("/{id}")
    public Animal buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Animal cadastrar(@Valid @RequestBody Animal animal) {
        return service.salvar(animal);
    }
    @PutMapping("/{id}")
    public Animal editar(@PathVariable Long id,
                         @Valid @RequestBody Animal animal) {
        return service.atualizar(id, animal);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}

