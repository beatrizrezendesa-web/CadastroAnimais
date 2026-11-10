
package br.com.projeto.cadastro_animais.service;

import br.com.projeto.cadastro_animais.model.Usuario;
import br.com.projeto.cadastro_animais.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // Listar usuários
    public List<Usuario> listar() {
        return repository.findAll();
    }

    // Buscar usuário pelo ID
    public Usuario buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"
                )
        );
    }

    // Cadastrar usuário
    public Usuario cadastrar(Usuario usuario) {

        if (repository.existsByLogin(usuario.getLogin())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Login já cadastrado"
            );
        }

        usuario.setId(null);
        usuario.setPerfil("FUNCIONARIO");
        usuario.setSenhaHash(
                encoder.encode(usuario.getSenhaHash())
        );

        return repository.save(usuario);
    }

    // Atualizar usuário
    public Usuario atualizar(Long id, Usuario dados) {

        Usuario usuario = buscar(id);

        usuario.setNome(dados.getNome());

        if (dados.getSenhaHash() != null &&
                !dados.getSenhaHash().isBlank()) {

            usuario.setSenhaHash(
                    encoder.encode(dados.getSenhaHash())
            );
        }

        return repository.save(usuario);
    }

    // Excluir usuário
    public void excluir(Long id) {
        Usuario usuario = buscar(id);
        repository.delete(usuario);
    }
}
