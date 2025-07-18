package com.icom.community.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum JobGradeCode {
    STAFF("A01","사원"),
    ASSISTANT_MANAGER("A02","대리"),
    MANAGER("D01","과장"),
    SENIOR_DIRECTOR("D02","차장"),
    DIRECTOR("V01","부장"),
    EXECUTIVE_DIRECTOR("V02","이사"),
    CEO("S01","대표이사");

    final String code;
    final String gradeName;

    public static JobGradeCode findByGradeName(String gradeName) {
        if (gradeName == null || gradeName.trim().isEmpty()) {
            return null;
        }

        for (JobGradeCode jobGrade : JobGradeCode.values()) {
            if (jobGrade.gradeName.equals(gradeName.trim())) {
                return jobGrade;
            }
        }
        return null;
    }

    public static JobGradeCode findByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }

        for (JobGradeCode jobGrade : JobGradeCode.values()) {
            if (jobGrade.code.equals(code.trim())) {
                return jobGrade;
            }
        }
        return null;
    }


    //직급명으로 코드 찾기
    public static String getCodeByGradeName(String gradeName) {
        JobGradeCode jobGrade = findByGradeName(gradeName);
        return jobGrade != null ? jobGrade.code : null;
    }

    //코드로 직급명 찾기
    public static String getGradeNameByCode(String code) {
        JobGradeCode jobGrade = findByCode(code);
        return jobGrade != null ? jobGrade.gradeName : null;
    }

    //직급명 유효성 확인
    public static boolean isValidGradeName(String gradeName) {
        return findByGradeName(gradeName) != null;
    }

    //직급코드 유효성 확인
    public static boolean isValidCode(String code) {
        return findByCode(code) != null;
    }



}
