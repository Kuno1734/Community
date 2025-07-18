package com.icom.community.domain.user.service;

import com.icom.community.common.enums.JobGradeCode;
import com.icom.community.common.excption.ServiceException;
import com.icom.community.common.excption.ServiceExceptionCode;
import com.icom.community.common.response.ApiResponse;
import com.icom.community.common.utils.EncryptStringUtil;
import com.icom.community.domain.user.dto.UserCreateRequest;
import com.icom.community.domain.user.dto.UserResponse;
import com.icom.community.domain.user.dto.UserUpdateRequest;
import com.icom.community.domain.user.entity.User;
import com.icom.community.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //신규 유저 등록
    @Transactional()
    public ApiResponse<UserResponse> createUser(UserCreateRequest request){
        //--------- PW 생성 ---------
        String planePw = request.getPassword();
        String encodedPw = EncryptStringUtil.hashPassword(planePw);
        if(encodedPw == null){
            throw new ServiceException(ServiceExceptionCode.PASSWORD_HASH_FAILED);
        }
        //--------- id검증 ---------
        if(isExistUser(request.getUserId())){
            throw new ServiceException(ServiceExceptionCode.USER_ALREADY_EXISTS);
        }
        //--------- id검증 ---------
        if(JobGradeCode.getCodeByGradeName(request.getJobGrade()) == null){
            throw new ServiceException(ServiceExceptionCode.NOT_FOUND_JOBCLASS);
        }

        LocalDate joinDate =  request.getJoinDate();
        LocalDateTime joinDateTime = joinDate.atStartOfDay();

        User user = User.builder()
                .userId(request.getUserId())
                .departmentCode(request.getDepartmentCode())
                .password(encodedPw)
                .loginCount(0)
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .jobGrade(request.getJobGrade())
                .auth("USER")
                .status('Y')
                .joinDate(joinDateTime)
                .build();
        userRepository.save(user);

        log.info("==== NEW USER CREATE SUCCESS ==== \n{}" , user);
        return ApiResponse.success(UserResponse.from(user));
    }

    //ID를 이용한 USER정보 검색
    @Transactional(readOnly = true)
    public ApiResponse<UserResponse> getUser(long id){
        User user = findUserById(id);
        return ApiResponse.success(UserResponse.from(user));
    }
    
    @Transactional
    public ApiResponse<UserResponse> updateUserInfo(long id ,UserUpdateRequest request){
        User user = findUserById(id);
        user.updateInfo(request);
        log.info("==== USER UPDATE INFO ==== \n{}" , user);
        userRepository.save(user);
        return ApiResponse.success(UserResponse.from(user));
    }

    private User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ServiceException(ServiceExceptionCode.NOT_FOUIND_USER)
        );
    }

    private Boolean isExistUser(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public ApiResponse<List<UserResponse>> findAllUser() {
        List<UserResponse> userResponseList = new ArrayList<>();
        for(User user : userRepository.findAll()){
            userResponseList.add(UserResponse.from(user));
        }
        return ApiResponse.success(userResponseList);
    }
}
