package com.standard.web_project.vo;
import lombok.Data;
@Data
public class MemberVO {
    private String userId;
    private String userPw;
    private String userName;
    private String phone;
    private String email;
    private String addr;
    private String detAddr;
    private String zipCode;
}