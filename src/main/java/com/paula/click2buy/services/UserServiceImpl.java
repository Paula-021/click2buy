package com.paula.click2buy.services;

import com.paula.click2buy.domain.User;
import com.paula.click2buy.exceptions.UserNotFoundException;
import com.paula.click2buy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        //o user que estamos recebendo no parametro tem senha. porém, ela ainda não está criptografada.
        // precisamos criptografar a senha antes de salvar o usuário no banco de dados.
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        return userRepository.save(user);

    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }

    //transacionais: insert, update, delete => o usuário solicita uma operação de alteração no banco e essa alteração geralmente não tem retorno
    //não transacional: select => o usuário solicita uma operação de consulta no banco e essa operação tem retorno

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
