package com.standard.web_project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Map<String, Object> processJoin(MemberVO memberVO) {
        Map <String, Object> response = new java.util.HashMap<>();
        String message = "";
        String result = "";

        System.out.println("Received MemberVO: " + memberVO.toString());

        try {
            memberService.registerMember(memberVO);
            message = "회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.";
            result = "success";
            response.put("redirectUrl", "/myPage");

        } catch (Exception e) {
            e.printStackTrace();
            message = "회원가입 중 오류가 발생했습니다.";
            result = "fail";
        }

        response.put("message", message);
        response.put("result", result);

        return response;
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

    @GetMapping("/checkId")
    @ResponseBody
    public boolean idDupCheck(@RequestParam String userId) {
        int exists = memberService.checkUserId(userId);
        
        if (exists > 0) {
            return true; // 중복됨
        } else {
            return false; // 중복 아님
        }
    }
    
}