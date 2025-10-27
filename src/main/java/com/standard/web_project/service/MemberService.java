package com.standard.web_project.service;

import com.standard.web_project.vo.MemberVO;

public interface MemberService {
    void registerMember(MemberVO memberVO);
    MemberVO loginMember(String userId, String rawPassword); // 로그인 기능
    void updateMember(MemberVO memberVO); // 정보 수정 기능
    MemberVO getMemberById(String userId); // 세션 갱신용
    int checkUserId(String userId); // 아이디 중복검사 기능
    
}
