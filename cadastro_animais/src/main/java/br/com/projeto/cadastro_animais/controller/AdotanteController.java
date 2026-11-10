package br.com.projeto.cadastro_animais.controller;


import br.com.projeto.cadastro_animais.model.Adotante;
import br.com.projeto.cadastro_animais.service.AdotanteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List
        ;
@RestController
@RequestMapping("/api/adotantes")
public class AdotanteController {
    private final AdotanteService service;
    public AdotanteController(AdotanteService service) { this.service = service; }
    @GetMapping public List<Adotante> listar() { return service.listar(); }
    @GetMapping("/{id}") public Adotante buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Adotante cadastrar(@Valid @RequestBody Adotante a) {
        return service.cadastrar(a);
    }
    @PutMapping("/{id}")
    public Adotante editar(@PathVariable Long id,
                           @Valid @RequestBody Adotante a) {
        return service.editar(id, a);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { service.excluir(id); }
}
