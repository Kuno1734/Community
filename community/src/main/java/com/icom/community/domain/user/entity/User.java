package com.icom.community.domain.user.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.icom.community.common.utils.EncryptStringUtil;
import com.icom.community.domain.user.dto.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Description;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TBL_USER_INFO")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String departmentCode;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int loginCount;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    //직급( 사원, 대리, 과장 , 차장, 부장, 이사, 대표 )
    @Column(nullable = false)
    private String jobGrade;

    //커뮤니티 권한 ( 관리자 S , 일반 U )
    @Column(nullable = false)
    private String auth;

    @Column(nullable = false)
    private char status;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public User(String userId, String departmentCode, String password, int loginCount, String name,
                String email, String phoneNumber, String address, String jobGrade,
                String auth, char status , LocalDateTime joinDate) {
        this.userId = userId;
        this.departmentCode = departmentCode;
        this.password = password;
        this.loginCount = loginCount;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.jobGrade = jobGrade;
        this.auth = auth;
        this.status = status;
        this.joinDate = joinDate;
    }

    public void updateInfo(UserUpdateRequest request) {
        this.departmentCode = request.getDepartmentCode();
        this.email = request.getEmail();
        this.address = request.getAddress();
        if (request.getPassword() != null) {
            this.password = EncryptStringUtil.hashPassword(request.getPassword());
        }
        this.phoneNumber = request.getPhoneNumber();
        this.jobGrade = request.getJobGrade();
        this.status = request.getStatus();
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .enable(SerializationFeature.INDENT_OUTPUT);    // ← add this

            Map<String, Object> userMap = new LinkedHashMap<>();
            userMap.put("id", id);
            userMap.put("userId", userId);
            userMap.put("departmentCode", departmentCode);
            userMap.put("password", "[PROTECTED]"); // 패스워드 마스킹
            userMap.put("loginCount", loginCount);
            userMap.put("name", name);
            userMap.put("email", email);
            userMap.put("phoneNumber", phoneNumber);
            userMap.put("address", address);
            userMap.put("jobGrade", jobGrade);
            userMap.put("auth", auth);
            userMap.put("status", status);
            userMap.put("joinDate", joinDate.toLocalDate());
            userMap.put("createdAt", createdAt);
            userMap.put("updatedAt", updatedAt);

            return mapper.writeValueAsString(userMap);
            // or: mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userMap);

        } catch (JsonProcessingException e) {
            return String.format("User{id=%d, userId=%s, error=%s}", id, userId, e.getMessage());
        }
    }
}
