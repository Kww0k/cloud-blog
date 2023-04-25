package com.cxcacm.user;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/ResSeat/GenerateCode");
            String path = url.getPath();
            System.out.println(path);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
