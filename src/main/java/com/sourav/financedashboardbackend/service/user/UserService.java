package com.sourav.financedashboardbackend.service.user;

import com.sourav.financedashboardbackend.RequestDto.*;
import com.sourav.financedashboardbackend.model.User;
import com.sourav.financedashboardbackend.response.UserResponseDto;
import org.springframework.data.domain.Page;



public interface UserService {
    Page<UserResponseDto> getAllUsers(int page , int size);

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request , Long userId);

    User updateSelf(Long userId, UpdateSelfRequest request);

    void deleteUser(Long userId);

    User assignRoleToUser(AssignRoleRequest request ,Long userId);

    User changeUserStatus(StatusChangeRequest request, Long userId);

    UserResponseDto convertUserToDto(User user);

}
