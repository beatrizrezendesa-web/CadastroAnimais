
package br.com.projeto.cadastro_animais.controller;

import br.com.projeto.cadastro_animais.model.Usuario;
import br.com.projeto.cadastro_animais.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Verifica se o usuário é administrador
    private void verificarAdmin(HttpServletRequest request) {
        var sessao = request.getSession(false);

        if (sessao == null ||
                !"ADMIN".equals(sessao.getAttribute("perfil"))) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Acesso restrito"
            );
        }
    }

    // Resposta sem expor a senha
    public record UsuarioResposta(
            Long id, String nome, String login, String perfil
    ) {
        public static UsuarioResposta de(Usuario usuario) {
            return new UsuarioResposta(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getLogin(),
                    usuario.getPerfil()
            );
        }
    }

    @GetMapping
    public List<UsuarioResposta> listar(HttpServletRequest request) {
        verificarAdmin(request);
        return service.listar().stream()
                .map(UsuarioResposta::de).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResposta buscar(
            @PathVariable Long id,
            HttpServletRequest request) {
        verificarAdmin(request);
        return UsuarioResposta.de(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResposta> cadastrar(
            @RequestBody Usuario usuario,
            HttpServletRequest request) {
        verificarAdmin(request);
        Usuario salvo = service.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UsuarioResposta.de(salvo));
    }

    @PutMapping("/{id}")
    public UsuarioResposta atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario,
            HttpServletRequest request) {
        verificarAdmin(request);
        return UsuarioResposta.de(
                service.atualizar(id, usuario)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            HttpServletRequest request) {
        verificarAdmin(request);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
