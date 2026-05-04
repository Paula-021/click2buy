package com.paula.click2buy.endpoints;

import com.paula.click2buy.domain.Address;
import com.paula.click2buy.domain.User;
import com.paula.click2buy.endpoints.dtos.UserRequestDTO;
import com.paula.click2buy.endpoints.dtos.UserResponseDTO;
import com.paula.click2buy.services.AddressService;
import com.paula.click2buy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //Bean => um componente gerenciado pelo Spring

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        User user = userService.addUser(userRequestDTO.toEntity());

        return ResponseEntity.status(201).body(new UserResponseDTO(user));//201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO, @PathVariable Long id) {

        User user = userRequestDTO.toEntity();
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().body("User updated successfully!");//200 OK
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User deleted successfully!");//200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO(user);
        return ResponseEntity.ok().body(userResponseDTO);//200 OK
    }
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponseDTO> userResponseDTOs = users.stream().map(UserResponseDTO::new).toList();

        return ResponseEntity.ok().body(userResponseDTOs);//200 OK
    }

    //criar um endpoint para o usuário adicionar endereços
    @PutMapping("/{id}/addresses/{idAddress}")
    public ResponseEntity<?> addAddressToUser(@PathVariable Long id, @PathVariable Long idAddress) {
        User user = userService.getUserById(id);
        Address address = addressService.getAddressById(idAddress);
        user.getAddresses().add(address);
        userService.updateUser(user);
        return ResponseEntity.ok().body("Addresses added to user successfully!");//200 OK
    }


}
