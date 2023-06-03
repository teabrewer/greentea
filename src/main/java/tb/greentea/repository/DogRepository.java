package tb.greentea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tb.greentea.entity.Dog;

public interface DogRepository extends JpaRepository<Dog, Integer> {

}
