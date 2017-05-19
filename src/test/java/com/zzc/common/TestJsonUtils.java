package com.zzc.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.core.security.XssIgnoreSerializer;
import com.zzc.core.utils.JsonUtils;

/**
 * Created by hadoop on 2015/12/3.
 */
public class TestJsonUtils {

    public static void main(String[] args) {
        TestPOJO testPOJO = new TestPOJO();
        testPOJO.setA("aa>a&c");
        testPOJO.setB("bb'b/c");
        testPOJO.setC("c>cc<a");

        System.out.println(JsonUtils.toJson(testPOJO));
    }


    public static class TestPOJO {
//        @JsonIgnore
        @JsonSerialize(using = XssIgnoreSerializer.class)
        private String a;
        private String c;
        private String d;
        private String b;
        //getters、setters省略

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
}
