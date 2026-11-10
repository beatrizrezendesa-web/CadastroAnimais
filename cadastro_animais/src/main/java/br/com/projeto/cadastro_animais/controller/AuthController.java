package br.com.projeto.cadastro_animais.controller;

import br.com.projeto.cadastro_animais.model.Usuario;
import br.com.projeto.cadastro_animais.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record LoginRequest(String login, String senha) {}

    public record UsuarioResposta(
            Long id,
            String nome,
            String login,
            String perfil
    ) {}

    @PostMapping("/login")
    public UsuarioResposta login(
            @RequestBody LoginRequest dados,
            HttpServletRequest request) {

        Usuario usuario = authService.autenticar(
                dados.login(), dados.senha()
        );

        // Impede reutilizar a sessão anterior ao login
        HttpSession sessaoAnterior = request.getSession(false);

        if (sessaoAnterior != null) {
            sessaoAnterior.invalidate();
        }

        HttpSession sessao = request.getSession(true);

        sessao.setAttribute("usuarioId", usuario.getId());
        sessao.setAttribute("perfil", usuario.getPerfil());

        return new UsuarioResposta(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }

    @GetMapping("/me")
    public UsuarioResposta usuarioLogado(
            HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("usuarioId") == null) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        Long id = (Long) sessao.getAttribute("usuarioId");

        return authService.buscarUsuario(id);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao != null) {
            sessao.invalidate();
        }
    }
}

