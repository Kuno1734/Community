package com.icom.community.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
public class UserUpdateRequest {
    @NotBlank(message = "부서코드는 필수입니다.")
    String departmentCode;

    @NotBlank(message = "이메일 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    String email;
    String address;

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    String phoneNumber;

    @NotBlank(message = "직급정보는 필수입니다.")
    String jobGrade;

    char status;
}
