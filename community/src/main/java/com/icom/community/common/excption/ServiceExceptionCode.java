package com.icom.community.common.excption;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ServiceExceptionCode {
    
    // 사용자 관련 오류
    NOT_FOUIND_USER("유저를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS("이미 존재하는 사용자입니다."),
    INVALID_USER_CREDENTIALS("잘못된 사용자 인증 정보입니다."),
    USER_NOT_AUTHORIZED("사용자 권한이 없습니다."),
    PASSWORD_HASH_FAILED("패스워드 암호화 오류 입니다."),
    NOT_FOUND_JOBCLASS("찾을 수 없는 직급 코드(이름) 입니다."),
    
    // 프로젝트 관련 오류
    NOT_FOUND_PROJECT("프로젝트를 찾을 수 없습니다."),
    PROJECT_ACCESS_DENIED("프로젝트 접근 권한이 없습니다."),
    PROJECT_ALREADY_EXISTS("이미 존재하는 프로젝트입니다."),
    
    // 이슈 관련 오류
    NOT_FOUND_ISSUE("이슈를 찾을 수 없습니다."),
    ISSUE_ACCESS_DENIED("이슈 접근 권한이 없습니다."),
    
    // 문서 관련 오류
    NOT_FOUND_DOCUMENT("문서를 찾을 수 없습니다."),
    DOCUMENT_ACCESS_DENIED("문서 접근 권한이 없습니다."),
    NOT_FOUND_FOLDER("폴더를 찾을 수 없습니다."),
    
    // 스케줄 관련 오류
    NOT_FOUND_SCHEDULE("스케줄을 찾을 수 없습니다."),
    SCHEDULE_CONFLICT("스케줄 충돌이 발생했습니다."),
    
    // 부서 관련 오류
    NOT_FOUND_DEPARTMENT("부서를 찾을 수 없습니다."),
    DEPARTMENT_ALREADY_EXISTS("이미 존재하는 부서입니다."),
    
    // 일반적인 오류
    INVALID_REQUEST("잘못된 요청입니다."),

    MISSING_REQUIRED_FIELD("필수 필드가 누락되었습니다."),
    INVALID_INPUT_FORMAT("잘못된 입력 형식입니다."),
    DATA_INTEGRITY_VIOLATION("데이터 무결성 오류가 발생했습니다."),
    RESOURCE_NOT_FOUND("요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    DATABASE_ERROR("데이터베이스 오류가 발생했습니다."),
    NETWORK_ERROR("네트워크 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR("파일 업로드 중 오류가 발생했습니다."),
    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    INVALID_FILE_FORMAT("지원하지 않는 파일 형식입니다."),
    FILE_SIZE_EXCEEDED("파일 크기가 제한을 초과했습니다."),
    OPERATION_NOT_ALLOWED("허용되지 않는 작업입니다."),
    CONCURRENT_MODIFICATION("동시 수정으로 인한 충돌이 발생했습니다."),
    SESSION_EXPIRED("세션이 만료되었습니다."),
    RATE_LIMIT_EXCEEDED("요청 제한을 초과했습니다.");
    
    final String message;
}