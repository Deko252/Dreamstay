package com.bitnc4.controller;

import com.bitnc4.dto.MemberDto;
import com.bitnc4.dto.QnaBoardDto;
import com.bitnc4.service.MemberService;
import com.bitnc4.service.QnaBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QnaboardController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private QnaBoardService qnaBoardService;
    @GetMapping("/mypage/qnaboard")
    public String quaboard(Model model, HttpSession session){
        // 세션에 저장된 id
        String writer = (String) session.getAttribute("userid");
        // 비회원일 경우 공란
        if (writer == null || writer.isEmpty()) {
            return "/mypage/qnaboard/qnaform";
        }
        MemberDto memberDto = qnaBoardService.searchIdOfinfo(writer);
        model.addAttribute("memberDto", memberDto);
        return "/mypage/qnaboard/qnaform";
    }
    @PostMapping("/insertqna")
    public String insertqna(QnaBoardDto dto,
                            HttpServletRequest request,
                            HttpSession session){

        //세션에 저장된 id
        String writer=(String)session.getAttribute("userid");

        // 비회원일시 nomember로 저장
        if (writer == null || writer.isEmpty()) {
            writer = "nomember";
        }
        //dto에 id 저장
        dto.setWriter(writer);

        qnaBoardService.insertqna(dto);

        return "redirect:/mypage/qnaboard";
    }

   @GetMapping("/mypage/qnalist")
   public String qnalist(QnaBoardDto dto, HttpSession session, HttpServletResponse response, Model model){
       //세션에 저장된 id
       String writer=(String)session.getAttribute("userid");

       // 비회원으로 로그인 시 로그인 페이지로 이동
       if (writer == null || writer.isEmpty()) {
            return "/main/signup/login";
       }
       //dto에 id 저장
       dto.setWriter(writer);

      List<QnaBoardDto> qnaBoardList = qnaBoardService.qnaList(writer);
      model.addAttribute("qnaBoardList", qnaBoardList);

         return "/mypage/qnaboard/qnalist";
     }

     @GetMapping("/mypage/qnadetail")
        public String detail(int num, Model model)
     {
         QnaBoardDto dto = qnaBoardService.getQna(num);
         model.addAttribute("dto",dto);

         return "/mypage/qnaboard/qnadetail";
     }

   }
