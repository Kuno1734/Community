package com.icom.community.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserCreateRequest {

    @Size(min = 2, max = 50 , message = "사용자이름은 최소2자 이상 30자 이하 입니다.")
    String userId;

    @NotBlank(message = "부서코드는 필수입니다.")
    String departmentCode;

    @NotBlank(message = "사용자 이름은 필수입니다.")
    String name;

    @NotBlank(message = "이메일 필수입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "이메일 형식이 올바르지 않습니다. (예: user@example.com)"
    )
    String email;

    String address;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상입니다.")
    String password;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    String phoneNumber;

    @NotBlank(message = "직급정보는 필수입니다.")
    String jobGrade;

    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate joinDate;
}
