package com.HiYo0;


import com.HiYo0.dto.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class _Controller {//class start

    @Autowired
    private Dao dao;

    // 페이지켜기
    @GetMapping("/articles/hi") // http://localhost/articles/hi
    public String newArticleForm(Model model){

        List<Dto> result = dao.view();
        // 2. p.175 뷰템플릿(머스테치)에게 전달할 값을 model 담아준다
        model.addAttribute("articleList",result);
        // 3. p.175 뷰 페이지 설정
        System.out.println("newArticleForm 켜짐");

        return "articles/hi";
    }

    // 개별조회
    @GetMapping("articles/{bno}") // http://localhost/articles/1
    public String show(@PathVariable int bno , Model model){
        System.out.println("단일 뷰 됨"+bno);
        // 1. no 조회해 데이터 가져오기
        Dto result =dao.showOne(bno);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("dtoForm",result);

        // 3. 뷰페이지 반환
        return "articles/show";
    }


    //글 저장
    @PostMapping("/articles/create") // http://localhost/articles/hi
    public String createArticle(Dto form, Model model){
        // DAO 에게 요청하고 응답받기.
        System.out.println("form = " + form);
        dao.creareArticle(form);
        try {
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println(addr);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/articles/hi";

    }

    // 글 수정
        // 불러오기
    @GetMapping("/articles/{bno}/edit")
    public String edit(@PathVariable int bno , Model model){
        System.out.println("bno 수정= " + bno);
        // 1. no 조회해 데이터 가져오기
        Dto result =dao.showOne(bno);
        model.addAttribute("dtoForm",result);

        return "articles/edit";
    }
        // 수정내용 저장하기
    @PostMapping("/article/update")
    public String update(Dto dto){
        System.out.println("수정내용 저장하기 실행됨"+dto);
        Dto updated =dao.update(dto);
        // 이동
        return "redirect:/articles/"+updated.getBno();
    }

    // 글 삭제
    @GetMapping("articles/{bno}/delete")
    public String delete(@PathVariable int bno){

        System.out.println("삭제 실행됨 = "+bno);
        boolean result = dao.delete(bno);


        return "redirect:/articles/hi";
    }



}//class end
