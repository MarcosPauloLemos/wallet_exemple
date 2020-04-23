package com.wallet.repository;

import com.wallet.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private static final String EMAIL = "email@teste.com";

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp(){
        User user = User.builder()
                .name("Usuario Inicial")
                .password("senha123")
                .email(EMAIL)
                .build();
        repository.save(user);
    }

    @After
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    public void testSave(){
        User user = User.builder()
                .name("Marcos")
                .password("123456")
                .email("marcospaulolemos0@gmail.com")
                .build();
        Assert.assertNotNull(repository.save(user));
    }

    @Test
    public void testFindByEmail(){
        Optional<User> user = repository.findByEmailEquals(EMAIL);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(user.get().getEmail(), EMAIL);
    }
}
