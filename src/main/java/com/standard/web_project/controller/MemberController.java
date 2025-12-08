package com.standard.web_project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder; // SecurityConfig가 만든 암호화 기계 주입

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
    @ResponseBody
    public Map<String, Object> processLogin(
            @RequestParam("userId") String userId,
            @RequestParam("userPw") String userPw,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();
        MemberVO loginMember = memberService.loginMember(userId, userPw);

        if (loginMember != null) {
            session.setAttribute("loginMember", loginMember);
            result.put("status", "success");
            result.put("redirectUrl", "/myPage");
        } else {
            result.put("status", "fail");
            result.put("message", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return result;
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
    @ResponseBody
    public Map<String, Object> updateMember(MemberVO memberVO, HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        // 세션에서 로그인 회원 정보 가져오기
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            result.put("status", "fail");
            result.put("message", "로그인이 필요합니다.");
            return result;
        }

        // userId는 세션 기준으로 강제 고정 (폼에서 넘어온 값과 맞추기)
        memberVO.setUserId(loginMember.getUserId());

        // 비밀번호가 입력된 경우에만 암호화해서 변경
        if (memberVO.getUserPw() != null && !memberVO.getUserPw().isBlank()) {
            String encodedPw = passwordEncoder.encode(memberVO.getUserPw());
            memberVO.setUserPw(encodedPw);
        } else {
            // 비밀번호 미변경: DB에 반영하지 않도록 서비스/매퍼에서 처리하거나
            // 여기서 기존 비번을 유지하는 식으로 구현
            memberVO.setUserPw(null);
        }

        // 서비스에 업데이트 요청
        memberService.updateMember(memberVO);

        // 세션 정보도 최신 값으로 갱신
        MemberVO updated = memberService.getMemberById(loginMember.getUserId());
        session.setAttribute("loginMember", updated);

        result.put("status", "success");
        result.put("message", "회원 정보가 수정되었습니다.");
        return result;
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