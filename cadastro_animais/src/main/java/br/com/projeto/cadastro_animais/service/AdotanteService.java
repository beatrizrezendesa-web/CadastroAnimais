package br.com.projeto.cadastro_animais.service;


import br.com.projeto.cadastro_animais.model.Adotante;
import br.com.projeto.cadastro_animais.repository.AdotanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AdotanteService {
    private final AdotanteRepository repo;
    public AdotanteService(AdotanteRepository repo) { this.repo = repo; }
    public List<Adotante> listar() { return repo.findAll(); }
    public Adotante buscar(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public Adotante cadastrar(Adotante novo) {
        novo.setId(null);
        novo.setCpf(novo.getCpf().replaceAll("\\D", ""));
        if (repo.existsByCpf(novo.getCpf()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF cadastrado");
        return repo.save(novo);
    }
    public Adotante editar(Long id, Adotante dados) {
        Adotante atual = buscar(id);
        String cpf = dados.getCpf().replaceAll("\\D", "");
        if (!cpf.equals(atual.getCpf()) && repo.existsByCpf(cpf))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF cadastrado");
        atual.setNome(dados.getNome()); atual.setCpf(cpf);
        atual.setEmail(dados.getEmail()); atual.setTelefone(dados.getTelefone());
        return repo.save(atual);
    }
    public void excluir(Long id) { repo.delete(buscar(id)); }
}

