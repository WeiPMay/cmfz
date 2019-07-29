package com.baizhi;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class TestWithoutSpring {
    @Test
    public void test1(){
        Md5Hash qwer = new Md5Hash("123456", "qwer", 1024);
        String s = qwer.toHex();
        System.out.println(s);
        //e6e407b1edb2cca3def82992c8ef32d9   散列一次
        //7f7cd15807fa0e4b278181e1f99767a1   散列两次
        //6c93325fc9a959026cd070fa4f7cf649   明文为:123456  MD5加密加盐并散列1024次的结果
    }
}
