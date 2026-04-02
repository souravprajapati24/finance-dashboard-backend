package com.sourav.financedashboardbackend.service.user;

import com.sourav.financedashboardbackend.RequestDto.*;
import com.sourav.financedashboardbackend.exceptions.AlreadyExistsException;
import com.sourav.financedashboardbackend.exceptions.UserNotFoundException;
import com.sourav.financedashboardbackend.model.User;
import com.sourav.financedashboardbackend.repositories.UserRepository;
import com.sourav.financedashboardbackend.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public Page<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable)
                .map(this::convertUserToDto);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User Not Found !"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setName(request.getName());
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setRole(request.getRole());
                    return userRepository.save(user);
                }).orElseThrow(()->new AlreadyExistsException("Oops! "+request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(
                exixtingUser->{
                    if(!request.getEmail().equals(exixtingUser.getEmail())){
                        if(userRepository.existsByEmail(request.getEmail())){
                            throw new AlreadyExistsException("Email Already in use !");
                        }
                        exixtingUser.setEmail(request.getEmail());
                    }
                    exixtingUser.setRole(request.getRole());
                    exixtingUser.setActive(request.getActive());
                    return userRepository.save(exixtingUser);
                }).orElseThrow(()->new UserNotFoundException("User Not Found !"));
    }

    @Override
    public User updateSelf(Long userId, UpdateSelfRequest request) {
        return userRepository.findById(userId).map(
                existingUser->{
                    existingUser.setName(request.getName());
                    existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,()->{
                    throw new UserNotFoundException("User Not found !");
                });
    }

    @Override
    public User assignRoleToUser(AssignRoleRequest request ,Long userId) {
        return userRepository.findById(userId).
                map(user -> {user.setRole(request.getRole());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException("User Not Found !"));
    }

    @Override
    public User changeUserStatus(StatusChangeRequest request , Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found !"));
        user.setActive(request.isNewStatus());
        userRepository.save(user);
        return user;
    }

    @Override
    public UserResponseDto convertUserToDto(User user) {
        return modelMapper.map(user,UserResponseDto.class);
    }

}
