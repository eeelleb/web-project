package com.standard.web_project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.standard.web_project.vo.MemberVO;

@Mapper
public interface MemberMapper {

    // 회원 가입
    @Insert("INSERT INTO member (userId, userPw, userName, phone, email, addr, detAddr, zipCode) VALUES (#{userId}, #{userPw}, #{userName}, #{phone}, #{email}, #{addr}, #{detAddr}, #{zipCode})")
    void insertMember(MemberVO memberVO);

    // 회원 가입
    //@Insert("INSERT INTO member (userId, userPw, userName) VALUES (#{userId}, #{userPw}, #{userName})")
    //void insertMember(MemberVO memberVO);

    // 아이디로 회원 정보 조회 (로그인, 정보 수정 시 사용)
    @Select("SELECT * FROM member WHERE userId = #{userId}")
    MemberVO getMemberById(String userId);

    // 회원 정보 수정 (비밀번호는 선택적으로 수정)
    @Update({
        "<script>",
        "UPDATE member SET userName = #{userName}",
        "<if test='userPw != null and userPw != \"\"'>",
        ", user_pw = #{userPw}",
        "</if>",
        "WHERE userId = #{userId}",
        "</script>"
    })
    void updateMember(MemberVO memberVO);
}