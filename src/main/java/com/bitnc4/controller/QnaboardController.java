package com.bitnc4.controller;

import com.bitnc4.dto.MemberDto;
import com.bitnc4.dto.QnaBoardDto;
import com.bitnc4.service.QnaBoardService;
import naver.cloud.NcpObjectStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Controller
public class QnaboardController {

    @Autowired
    private QnaBoardService qnaBoardService;

    @Autowired
    private NcpObjectStorageService storageService;
    String bucketName = "dreamsstaybucket";

    @GetMapping("/mypage/qnaboard")
    public String quaboard(Model model, HttpSession session) {
        // 세션에 저장된 id
        String writer = (String) session.getAttribute("userid");
        // 비회원일 경우 공란
        if (writer == null || writer.isEmpty()) {
            return "/mypage/qnaboard/qnaform";
        }
        MemberDto memberDto = qnaBoardService.searchIdOfinfo(writer);
        model.addAttribute("memberDto", memberDto);

        // info user name 출력 <상혁>
        MemberDto dto = (MemberDto) session.getAttribute("loginuser");
        String[] fnFn = dto.getUser_name().split("/");
        model.addAttribute("familyname", fnFn[0]);
        model.addAttribute("firstname", fnFn[1]);
        return "/mypage/qnaboard/qnaform";
    }

    @PostMapping("/insertqna")
    public String insertqna(QnaBoardDto dto,
                            HttpServletRequest request,
                            HttpSession session,
                            MultipartFile photo) {

        String filename = "";
        //업로드를 한 경우만 버킷에 이미지 저장
        if(!photo.getOriginalFilename().equals("")){
            filename = storageService.uploadFile(bucketName, "qnaboard", photo);
        }



        // dto에 photo 저장\
        dto.setQna_photo(filename);


   /*     if(photo!=null) {
            System.out.println("size:"+photo.size());

            for(MultipartFile file:photo)
            {
                //List<String> photoNames=new ArrayList<>();
                //스토리지에 업로드하기
                String photoname=storageService.uploadFile(bucketName, "qnaboard", file);
                //업로드한 파일명을 db 에 저장
                dto.setQna_photo(photoname);

            }
        }*/

        // 아이디 저장 세션에 저장된 id
        String writer=(String)session.getAttribute("userid");

        // 비회원일시 nomember로 저장
        if (writer == null || writer.isEmpty()) {
            writer = "nomember";
        }
        //dto에 id 저장
        dto.setWriter(writer);

        //radio 값 저장
        String qnaType = request.getParameter("qna_type");
        dto.setQna_type(qnaType);

        // 사용일자 파싱
        String usedayStr = request.getParameter("useday");
        System.out.println(usedayStr);

        String resrevenum = request.getParameter("resrevenum");
        dto.setReservenum(resrevenum);

        qnaBoardService.insertqna(dto);

        return "redirect:/mypage/qnalist";
    }

    @GetMapping("/mypage/qnalist")
    public String qnalist(QnaBoardDto dto, HttpSession session, HttpServletResponse response,
                          Model model, @RequestParam(defaultValue = "1") int currentPage){
        //세션에 저장된 id
        String writer=(String)session.getAttribute("userid");

        // 비회원으로 로그인 시 로그인 페이지로 이동
        if (writer == null || writer.isEmpty()) {
            return "/main/signup/login";
        }
        //dto에 id 저장
        dto.setWriter(writer);

       //

        //게시판의 총 글갯수 얻기
        int totalCount= qnaBoardService.getQnaCount(writer);
        int totalPage;//총 페이지수
        int perPage=5; //한 페이지당 보여질 글의 갯수
        int perBlock=10;//한 블럭당 보여질 페이지의 갯수
        int startNum;//각 페이지에서 보여질 글의 시작번호
        int startPage;//각 블럭에서 보여질 시작 페이지번호
        int endPage;//각 블럭에서 보여질 끝 페이지번호
        int no;//글 출력시 출력할 시작번호

        //총 페이지수
        totalPage=totalCount/perPage+(totalCount%perPage==0?0:1);
        //시작페이지
        startPage=(currentPage-1)/perBlock*perBlock+1;
        //끝페이지
        endPage=startPage+perBlock-1;
        //이때 문제점....endPage 가 totalpage 보다 크면 안된다
        if(endPage>totalPage)
            endPage=totalPage;

        //각 페이지의 시작번호(1페이지 : 0, 2페이지:3,3페이지 :6...)
        startNum=(currentPage-1)*perPage;
        //각 글마다 출력할 글번호(예:10개일경우 1페이지:10, 2페이지: 7...
        //no=totalCount-(currentPage-1)*perPage;
        no=totalCount-startNum;

        //각 페이지에 필요한 게스글 db 에서 가져오기
        List<QnaBoardDto> qnaBoardList = qnaBoardService.qnaList(startNum,perPage,writer);

        //출력시 필요한 변수들을 model 에 몽땅 저장
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("qnaBoardList", qnaBoardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("no", no);

        // info user name 출력 <상혁>

        MemberDto dto1 = (MemberDto) session.getAttribute("loginuser");
        String[] fnFn = dto1.getUser_name().split("/");
        model.addAttribute("familyname", fnFn[0]);
        model.addAttribute("firstname", fnFn[1]);

        return "/mypage/qnaboard/qnalist";
    }

    @GetMapping("/mypage/qnadetail")
    public String detail(int num, Model model, HttpSession session) {
        QnaBoardDto dto = qnaBoardService.getQna(num);
        model.addAttribute("dto", dto);

        // info user name 출력 <상혁>
        MemberDto dto1 = (MemberDto) session.getAttribute("loginuser");
        String[] fnFn = dto1.getUser_name().split("/");
        model.addAttribute("familyname", fnFn[0]);
        model.addAttribute("firstname", fnFn[1]);

        return "/mypage/qnaboard/qnadetail";
    }

    @GetMapping("/mypage/deleteqna")
    public String deleteqna(int num, HttpSession session, Model model) {
        //db 삭제 전에 저장된 이미지 버켓에서 지운다
        String filename = qnaBoardService.getQna(num).getQna_photo();
        if (filename != null && !filename.equals("")) {
            storageService.deleteFile(bucketName, "qnaboard", filename);
        }
        //db 삭제
        qnaBoardService.deleteQna(num);

        // info user name 출력 <상혁>
        MemberDto dto = (MemberDto) session.getAttribute("loginuser");
        String[] fnFn = dto.getUser_name().split("/");
        model.addAttribute("familyname", fnFn[0]);
        model.addAttribute("firstname", fnFn[1]);

        return "redirect:/mypage/qnalist";
    }

 /*   @PostMapping("/updateQnaBoard")
    public String updateQnaBoard(QnaBoardDto dto,MultipartFile photo,int currentPage){

        String filename="";
        if(!photo.getOriginalFilename().equals("")) {
            //기존 파일명 알아내기
            filename=qnaBoardService.getQna(dto.getNum()).getQna_photo();
            //버켓에서 삭제
            storageService.deleteFile(bucketName, "qnaboard", filename);

            //다시 업로드후 업로드한 파일명 얻기
            filename=storageService.uploadFile(bucketName, "qnaboard", photo);
        }
        dto.setQna_photo(filename);

        qnaBoardService.updateQnaBoard(dto);

        return "/mypage/qnadetail?num="+dto.getNum()+"&currentPage="+currentPage;

    }

    @GetMapping("/mypage/updateqna")
    public String updateqna(int num,int currentPage,Model model)
    {
        QnaBoardDto dto=qnaBoardService.getQna(num);

        model.addAttribute("dto", dto);
        model.addAttribute("currentPage", currentPage);

        return "/mypage/qnaboard/qnaupdateform";
    }
*/


}