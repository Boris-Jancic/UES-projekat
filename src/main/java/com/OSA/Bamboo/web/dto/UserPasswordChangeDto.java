package com.OSA.Bamboo.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPasswordChangeDto {

    private String username;

    private String oldPassword;

    private String password;

    private String passwordConfirm;
}
