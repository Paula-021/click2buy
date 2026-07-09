package com.paula.click2buy.services;

import com.paula.click2buy.domain.User;
import com.paula.click2buy.exceptions.UserNotFoundException;
import com.paula.click2buy.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //usar @Mock para as dependencias da classe que vc está testando => usar @Mocks para as instancias genericas

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    //usar @InjectMocks para a classe que vc está testando => usar @InjectMocks para a instancia concreta da classe que vc quer testar

    @InjectMocks
    private UserServiceImpl userService;


    //deve encriptar a senha e salvar o usuário
    @Test
    void shouldEncryptPasswordAndSaveUser() {
        // AAA Pattern: Arrange, Act, Assert => Preparar, Agir, Verificar

        //Arrange (preparar o cenário)
        User user = new User();
        user.setEmail("julia@gmail.com");
        user.setPassword("password123");

        //Act (agir)

        //quando o método encode do passwordEncoder for chamado com a senha "password123", ele deve retornar uma senha encriptada.
        when(passwordEncoder.encode("password123")).thenReturn("codeDaPaula");

        //quando o método save do userRepository for chamado com qualquer objeto do tipo User, ele deve retornar o mesmo objeto User que foi passado como parâmetro.
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.addUser(user);

        //Assert
        assertEquals("codeDaPaula", savedUser.getPassword());
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(user);




    }
    @Test
    //Deve chamar o método save ao atualizar um usuário
    void shouldSaveWhenUpdatingUser(){
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("julia@gmail.com");
        user.setPassword("password123");

        // Act
        userService.updateUser(user);

        // Assert
        verify(userRepository).save(user);

    }
    @Test
    //Deve chamar o método deleteById ao deletar um usuário
    void shouldDeleteUserWhenUserExists(){
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }
    //Deve chamar o método findById ao buscar um usuário por ID
    @Test
    void shouldReturnUserWhenExists(){
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(userId);

        // Assert
        assertEquals(user, foundUser);
        verify(userRepository).findById(userId);
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(userId);
        });

    }

    //Deve chamar o método findAll ao buscar todos os usuários
    @Test
    void shouldFindAllUsers(){
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
        verify(userRepository).findAll();
    }
}
