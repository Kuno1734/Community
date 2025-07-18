package com.icom.community.common.excption;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceException extends RuntimeException {

    private String code;
    private String message;

    public ServiceException(ServiceExceptionCode response){
        super(response.getMessage());
        this.code = response.name();
        this.message = super.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
