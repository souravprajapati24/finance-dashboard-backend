package com.sourav.financedashboardbackend.controllers;


import com.sourav.financedashboardbackend.RequestDto.*;
import com.sourav.financedashboardbackend.model.User;
import com.sourav.financedashboardbackend.response.ApiResponse;
import com.sourav.financedashboardbackend.response.UserResponseDto;
import com.sourav.financedashboardbackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<UserResponseDto> userDtos = userService.getAllUsers(page,size);
        return ResponseEntity.status(OK).body(new ApiResponse("Users.",userDtos));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        UserResponseDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.status(OK).body(new ApiResponse("User Found.",userDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        User newUser = userService.createUser(request);
        UserResponseDto userDto = userService.convertUserToDto(newUser);
        return ResponseEntity.status(CREATED).body(new ApiResponse("User Created.",userDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/user/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserUpdateRequest request ,
                                                  @PathVariable Long id){
        User updatedUser = userService.updateUser(request,id);
        UserResponseDto userDto = userService.convertUserToDto(updatedUser);
        return ResponseEntity.status(OK).body(new ApiResponse("User Updated.",userDto));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @PatchMapping ("/update/self/{id}")
    public ResponseEntity<ApiResponse> updateSelf(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateSelfRequest request){
        User user = userService.updateSelf(id,request);
        UserResponseDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.status(OK).body(new ApiResponse("Update Successful", userDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(OK).body(new ApiResponse("User Deleted.",null));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/role/{id}")
    public ResponseEntity<ApiResponse> assignRoleToUser(@Valid @RequestBody AssignRoleRequest request,
                                                        @PathVariable Long id){
        User user = userService.assignRoleToUser(request,id);
        UserResponseDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.status(OK).body(new ApiResponse("Role Assigned.",userDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/status/{id}")
    public ResponseEntity<ApiResponse> changeStatus(@Valid @RequestBody StatusChangeRequest request , @PathVariable Long id){
        User user = userService.changeUserStatus(request,id);
        UserResponseDto userDto = userService.convertUserToDto(user);
        return ResponseEntity.status(OK).body(new ApiResponse("Status changed.",userDto));
    }
}






















