package com.atguigu.common.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTHelperTest {

    @Test
    void creatToken01() {
        long a=1;
        String token = JWTHelper.creatToken(a, "admin");
        System.out.println("token == " + token);

        Long userId = JWTHelper.getUserId(token);
        String username=JWTHelper.getUsername(token);
        System.out.println("userId == " + userId);
        System.out.println("username == " + username);
    }
}