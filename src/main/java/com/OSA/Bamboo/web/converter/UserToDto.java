package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.User;
import com.OSA.Bamboo.web.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component

public class UserToDto implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setBlocked(user.isBlocked());
        dto.setRole(user.getRole());

        return dto;
    }

    public List<UserDto> convert(List<User> users) throws IOException {

        List<UserDto> retVal = new ArrayList<>();

        for (User u : users) {
            UserDto dto = this.convert(u);
            retVal.add(dto);
        }

        return retVal;
    }
}
