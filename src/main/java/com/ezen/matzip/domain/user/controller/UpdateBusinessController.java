package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.user.entity.Business;
import com.ezen.matzip.domain.user.service.BusinessUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UpdateBusinessController {

    @Autowired
    private BusinessUpdateService businessUpdateService;

    @GetMapping("/business/myinfo")
    public String editBusinessInfo(Model model, Principal principal) {
        Business user = businessUpdateService.findByUserId(principal.getName());
        model.addAttribute("user", user);

        return "domain/store/store_myinfo";
    }
}
