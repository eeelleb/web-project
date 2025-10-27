package com.standard.web_project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.standard.web_project.service.MemberService;
import com.standard.web_project.vo.MemberVO;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // --- 회원가입 관련 ---
    @GetMapping("/joinForm")
    public String showJoinForm() {
        return "joinForm";
    }

    

    @PostMapping("/joinAction")
    @ResponseBody
    public Map<String, String> processJoin(@ModelAttribute MemberVO memberVO) {
        System.out.println("Received MemberVO: " + memberVO.toString());
        return Map.of(); // 임시 반환
    }
    public String processJoin(MemberVO memberVO, Model model) {
        
        System.out.println("Received MemberVO: " + memberVO.toString());
        //userId 중복검사
        int userId = memberService.checkUserId(memberVO.getUserId());

        if(userId > 0) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "redirect:/joinForm"; // 중복된 아이디가 있을 경우 에러 표시
        } else {
            memberService.registerMember(memberVO);
            return "redirect:/loginForm"; // 가입 성공 후 로그인 폼으로 리다이렉트
        }
        
    }

    // --- 로그인 & 로그아웃 관련 ---
    @GetMapping("/loginForm")
    public String showLoginForm() {
        return "loginForm";
    }

    @PostMapping("/loginAction")
    public String processLogin(
            @RequestParam("userId") String userId,
            @RequestParam("userPw") String userPw,
            HttpSession session, Model model) {
            
        MemberVO loginMember = memberService.loginMember(userId, userPw);
        
        if (loginMember != null) {
            // 로그인 성공 시, 세션에 회원 정보를 저장합니다.
            session.setAttribute("loginMember", loginMember);
            return "redirect:/myPage"; // 성공 시 마이페이지로 리다이렉트
        } else {
            // 실패 시, 에러 메시지를 모델에 담아 다시 로그인 폼으로 보냅니다.
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "loginForm";
        }
    }

    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        session.invalidate(); // 세션을 무효화하여 로그아웃 처리
        return "redirect:/loginForm";
    }

    // --- 마이페이지 관련 (단 하나의 메소드만 남깁니다) ---
    @GetMapping("/myPage")
    public String showMyPage(HttpSession session, Model model) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        
        if (loginMember == null) {
            // 비로그인 상태라면 로그인 폼으로 보냅니다.
            return "redirect:/loginForm";
        }
        
        // 로그인 상태라면, 모델에 회원 정보를 담아 myPage.jsp로 보냅니다.
        model.addAttribute("member", loginMember);
        return "myPage";
    }

    @PostMapping("/updateAction")
    public String processUpdate(MemberVO memberVO, HttpSession session) {
        memberService.updateMember(memberVO);
        // 세션 정보도 최신으로 갱신해 줍니다.
        MemberVO updatedMember = memberService.getMemberById(memberVO.getUserId());
        session.setAttribute("loginMember", updatedMember);
        return "redirect:/myPage";
    }
}