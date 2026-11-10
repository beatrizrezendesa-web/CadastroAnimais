package br.com.projeto.cadastro_animais.service;


import br.com.projeto.cadastro_animais.controller.AuthController;
import br.com.projeto.cadastro_animais.model.Usuario;
import br.com.projeto.cadastro_animais.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario autenticar(String login, String senha) {

        Usuario usuario = repository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Login ou senha inválidos"
                ));

        if (senha == null ||
                !encoder.matches(senha, usuario.getSenhaHash())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Login ou senha inválidos"
            );
        }

        return usuario;
    }


    public AuthController.UsuarioResposta buscarUsuario(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário não encontrado"
                ));

        return new AuthController.UsuarioResposta(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }

}

