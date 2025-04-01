package com.ezen.matzip.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping(value={"/", "/main"})
    public String guestMain() {
        return "main/main";
    }
//
//    @GetMapping("/user/main")
//    public String userMain() {
//        return "main/main";
//    }
//
    @GetMapping("/login")
    public String login() {
        return "common/login";
    }

    @GetMapping("/fragments/userheader")
    public String getUserHeader() {
        return "html/user_header";
    }

    @GetMapping("/fragments/userfooter")
    public String getUserFooter() {
        return "html/user_footer";
    }

}
