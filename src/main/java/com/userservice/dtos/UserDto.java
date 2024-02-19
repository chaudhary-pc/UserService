package com.userservice.dtos;
import com.userservice.models.Role;
import com.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {

        private String name;
        private String email;
        private List<Role> roles;
        private boolean isEmailVerified;

        public static UserDto convertUserToUserDto(User user) {
            if(user == null) return null;
            UserDto userDto = new UserDto();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setRoles(user.getRoles());
            userDto.setEmailVerified(user.isEmailVerified());
            return userDto;
        }
}
