package com.HiYo0.dto;

import lombok.*;

@NoArgsConstructor
@Getter@Setter@ToString
public class Dto {//class start
    private int bno;
    private String bname;
    private String bcontent;

    public Dto(int bno, String bname, String bcontent) {
        this.bno = bno;
        this.bname = bname;
        this.bcontent = bcontent;
    }
}//class end

