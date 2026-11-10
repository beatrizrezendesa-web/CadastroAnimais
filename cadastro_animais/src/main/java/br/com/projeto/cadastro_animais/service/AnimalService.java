package br.com.projeto.cadastro_animais.service;

import br.com.projeto.cadastro_animais.model.Animal;
import br.com.projeto.cadastro_animais.repository.AnimalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }
    public List<Animal> listar() {
        return repository.findAll();
    }
    public Animal buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Animal não encontrado"));
    }
    public Animal salvar(Animal animal) {
        validar(animal);
        animal.setId(null);
        return repository.save(animal);
    }
    public Animal atualizar(Long id, Animal dados) {
        Animal atual = buscar(id);
        validar(dados);
        atual.setNome(dados.getNome());
        atual.setEspecie(dados.getEspecie());
        atual.setRaca(dados.getRaca());
        atual.setIdadeAproximada(dados.getIdadeAproximada());
        atual.setSexo(dados.getSexo());
        atual.setPorte(dados.getPorte());
        atual.setStatus(dados.getStatus());
        return repository.save(atual);
    }
    public void excluir(Long id) {
        Animal animal = buscar(id);
        repository.delete(animal);
    }
    private void validar(Animal animal) {
        if (animal.getIdadeAproximada() != null
                && animal.getIdadeAproximada() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Idade não pode ser negativa");
        }
        String status = animal.getStatus();
        if (!"DISPONIVEL".equals(status) && !"ADOTADO".equals(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Status inválido");
        }
    }
}
