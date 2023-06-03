package tb.greentea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tb.greentea.entity.Human;

public interface HumanRepository extends JpaRepository<Human, Integer> {

}
