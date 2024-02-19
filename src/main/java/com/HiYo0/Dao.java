package com.HiYo0;

import com.HiYo0.dto.Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Dao {
    // 1. 필드
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    // 2. 생성자 : db연동 코드

    public Dao() {
        try {
            // 1. jdbc 라이브러리 호출
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 연동
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hiyo0",
                    "root","1234");
            System.out.println("DB success");
        }catch (Exception e){
            log.error(e.toString());
        }
    }//method end
    //=============================================================//

    public boolean creareArticle(Dto dto){//쓰기
        try {
            String sql ="insert into box (bname , bcontent )values(?,?);";
            ps = conn.prepareStatement( sql );
            ps.setString(1, dto.getBname());
            ps.setString(2, dto.getBcontent());
            // 4.
            int count = ps.executeUpdate();
            // 5.
            if(count==1){return true;}
        }catch (Exception e){
            System.out.println(e);
        }

        return false;
    }

    public List<Dto> view(){ //전체보기
        List<Dto> list = new ArrayList<>();
        try {
            String sql = "select * from box;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                // 1. 객체 만들기
                Dto form =new Dto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                // 2. 객체를 리스트에 넣기
                list.add(form);
            }//while end
            return list;
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }

    public Dto showOne(int bno){
        Dto dto =new Dto();
        try {
            String sql = "select * from box where bno = "+bno+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                // 1. 객체 만들기
                dto.setBno(rs.getInt(1));
                dto.setBname(rs.getString(2));
                dto.setBcontent(rs.getString(3));

                return dto; // 찾으면 반환
            }//if end
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return dto;
    }

    // 글수정
    public Dto update(Dto dto){
        try {
            String sql = "update box set bname=? ,bcontent = ? where bno =?";
            ps = conn.prepareStatement(sql);
            ps.setLong(3,dto.getBno());
            ps.setString(1,dto.getBname());
            ps.setString(2,dto.getBcontent());
            int count = ps.executeUpdate();
            if(count==1){
                return dto;
            }
        }catch (Exception e){System.out.println(e);}
        return null;
    }

    // 글 삭제
    public boolean delete(int bno){
        try {
            String sql = "delete from box where bno = "+bno+";";
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate();
            if(count==1){
                return true;
            }
        }catch (Exception e){System.out.println(e);}
        return false;
    }



}//class end
