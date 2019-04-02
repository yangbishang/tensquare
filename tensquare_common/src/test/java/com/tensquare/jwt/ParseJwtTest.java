package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser()
                .setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NTM5MTA5MzgsImV4cCI6MTU1MzkxMDk5OCwicm9sZSI6ImFkbWluIn0.TSRDkK_bVqyeKWL3iaDDJugtZ-1F3QmPN0R1VMURGPw")
                .getBody();

        System.out.println("用户id:"+claims.getId());
        System.out.println("用户名："+claims.getSubject());
        System.out.println(String.format("登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt())));
        System.out.println("过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
    }
}
