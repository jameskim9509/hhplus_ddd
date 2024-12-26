package com.hhplus.ddd.infrastructure.core;

import com.hhplus.ddd.domain.user.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureTestDatabase(
        connection= EmbeddedDatabaseConnection.H2,
        replace = AutoConfigureTestDatabase.Replace.ANY
)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class UserRepositoryImplTest {
    @Autowired
    UserJpaRepository userJpaRepository;
    UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp()
    {
        userRepository = new UserRepositoryImpl(userJpaRepository);
    }

    @Test
    void getById()
    {
        // when
        UserEntity user = userRepository.getById(1L).get();

        //then
        Assertions.assertThat(user.getId()).isEqualTo(1L);
        Assertions.assertThat(user.getUsername()).isEqualTo("aimy");
    }
}