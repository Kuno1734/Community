package com.icom.community.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icom.community.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private long id;
    private String userId;
    private String departmentCode;
    private String userName;
    private String address;
    private String phoneNumber;
    private char status;
    private String email;
    private String jobGrade;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;


    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .departmentCode(user.getDepartmentCode())
                .userName(user.getName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .email(user.getEmail())
                .jobGrade(user.getJobGrade())
                .joinDate(user.getJoinDate().toLocalDate())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
