package com.standard.web_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.standard.web_project.mapper.MemberMapper;
import com.standard.web_project.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder; // SecurityConfig가 만든 암호화 기계 주입

    @Override
    public MemberVO loginMember(String userId, String rawPassword) {
        // 1. 아이디로 DB에서 암호화된 비밀번호가 포함된 회원 정보를 가져옵니다.
        MemberVO member = memberMapper.getMemberById(userId);
        
        // 2. 회원이 존재하고, 입력된 비밀번호와 DB의 암호화된 비밀번호가 일치하는지 확인합니다.
        if (member != null && passwordEncoder.matches(rawPassword, member.getUserPw())) {
            return member; // 일치하면 회원 정보 전체를 반환
        }
        return null; // 일치하지 않으면 null 반환
    }

    @Override
    public void updateMember(MemberVO memberVO) {
        // 비밀번호 필드가 비어있지 않은 경우에만 암호화하여 업데이트
        if (memberVO.getUserPw() != null && !memberVO.getUserPw().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(memberVO.getUserPw());
            memberVO.setUserPw(encodedPassword);
        }
        memberMapper.updateMember(memberVO);
    }
    
    @Override
    public MemberVO getMemberById(String userId) {
        return memberMapper.getMemberById(userId);
    }

    @Override
    public void registerMember(MemberVO memberVO) {

        // 사용자가 입력한 개인정보 암호화
        String encodedPassword = passwordEncoder.encode(memberVO.getUserPw());
        String encodedUserName = passwordEncoder.encode(memberVO.getUserName());
        String encodedEmail = passwordEncoder.encode(memberVO.getFrontEmail() + "@" + memberVO.getBackEmail());
        String encodedPhone = passwordEncoder.encode(memberVO.getPhone());
        String encodedAddr = passwordEncoder.encode(memberVO.getAddr());
        String encodedDetAddr = passwordEncoder.encode(memberVO.getDetAddr());

        // 암호화된 개인정보를 VO 객체에 다시 설정
        memberVO.setUserPw(encodedPassword);
        memberVO.setUserName(encodedUserName);
        memberVO.setEmail(encodedEmail);
        memberVO.setPhone(encodedPhone);
        memberVO.setAddr(encodedAddr);
        memberVO.setDetAddr(encodedDetAddr);

        // 3. 암호화된 정보가 담긴 VO를 DB로 전달
        memberMapper.insertMember(memberVO);
    }

    @Override
    public int checkUserId(String userId) {
        MemberVO member = memberMapper.getMemberById(userId);
        return (member != null) ? 1 : 0; // 존재하면 1, 없으면 0 반환
    }
}