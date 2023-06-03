package tb.greentea.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import tb.greentea.entity.Dog;
import tb.greentea.entity.Human;

@DataJpaTest(showSql = true)
@Slf4j
public class RepositoryTests {

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private DogRepository dogRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Integer savedHumanId;
    private Integer savedDogId;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        Human cha = humanRepository.save(
            Human.builder()
                .name("차무식")
                .age(40)
                .build());
        savedHumanId = cha.getHumanSeq();

        Dog happy = dogRepository.save(
            Dog.builder()
                .name("해피")
                .age(1)
                .build());
        savedDogId = happy.getDogSeq();
    }

    @DisplayName("리파지토리 테스트")
    @Test
    void repositoryTest() {

        Human cha = humanRepository.findById(savedHumanId).orElse(null);
        Dog happy = dogRepository.findById(savedDogId).orElse(null);

        try {
            log.error("##>> HUMAN: {}", objectMapper.writeValueAsString(cha));
            log.error("##>> DOG: {}", objectMapper.writeValueAsString(happy));
        } catch (JsonProcessingException e) {
            log.error("##>> objectMapper is babo.", e);
        }

        assertThat(cha).isNotNull();
        assertThat(cha.getName()).isEqualTo("차무식");

        assertThat(happy).isNotNull();
        assertThat(happy.getName()).isEqualTo("해피");

        cha.addDog(happy.getName());

        Human h1 = humanRepository.save(cha);

        assertThat(h1.getDogs()).hasSize(1);
    }

}
