package com.standard.web_project.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.standard.web_project.mapper.MemberMapper;
import com.standard.web_project.vo.MemberVO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. username(userId) DB에서 회원 정보 조회
        MemberVO member = memberMapper.getMemberById(username);

        // 2. 만약 회원이 존재하지 않으면, 예외 발생
        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // 3. 조회된 회원 정보를 Spring Security가 이해할 수 있는 UserDetails 객체로 변환하여 리턴
        return new User(member.getUserId(), member.getUserPw(), Collections.emptyList());
    }
}