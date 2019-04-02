package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")              //负载
                .setSubject("小马")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256 , "itcast")   //盐
                .setExpiration(new Date(new Date().getTime() + 60000))  //设置过期时间
                .claim("role" , "admin");     //添加自定义属性
        System.out.println(jwtBuilder.compact());
    }
}
