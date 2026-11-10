package br.com.projeto.cadastro_animais.repository;


import br.com.projeto.cadastro_animais.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByStatusIgnoreCase(String status);
}
