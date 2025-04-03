package com.ezen.matzip.domain.minigame.controller;

import com.ezen.matzip.domain.minigame.service.MiniGameService;
import com.ezen.matzip.domain.weather.dto.KeywordDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MiniGameController {

    private final MiniGameService miniGameService;

    @GetMapping("/minigame/first")
    public String firstMiniGame(Model model, HttpSession session) {

        model.addAttribute("announce", "32강 시작!");
        List<KeywordDTO>[] firstKeywords = miniGameService.shuffleKeywords();
        firstKeywords = miniGameService.showKeywords(firstKeywords);
        session.setAttribute("currentKeywords", firstKeywords);
        model.addAttribute("currentKeywords", firstKeywords);

        return "domain/minigame/user_minigame";
    }

    @GetMapping("/minigame/select")
    public String getSelected(@RequestParam("selected") String selected, HttpSession session, Model model)
    {
        return "domain/minigame/user_minigame";
    }

    @PostMapping("/minigame/select")
    public String showKeyword(@RequestParam("selected") String selected, HttpSession session, Model model) {
        // 세션에서 currentKeywords 가져오기
        List<KeywordDTO>[] currentKeywords = (List<KeywordDTO>[]) session.getAttribute("currentKeywords");
        String announce = miniGameService.checkCurrentRound(currentKeywords);
        if (announce != null)
        {
            if (announce.equals("끝!"))
            {
                KeywordDTO lastKeyword = miniGameService.foundKeyword(selected);
                model.addAttribute("lastKeyword", lastKeyword);
                return "domain/minigame/user_minigameresult";
            }
            else
            {
                model.addAttribute("announce", announce);
            }
        }

        currentKeywords = miniGameService.sortKeyword(currentKeywords, selected);
        currentKeywords = miniGameService.showKeywords(currentKeywords);

        model.addAttribute("currentKeywords", currentKeywords);
        return "domain/minigame/user_minigame";
    }

//    @GetMapping("/minigame/last")
//    public String LastMiniGame(Model model) {
//
//        KeywordDTO lastKeyword = miniGameService.lastKeyword(keywords);
//        model.addAttribute()
//        return "/domain/minigame/user_minigameresult";
//    }

}

//minigame/ → 미니게임 기능 (추천 음식 선택)

//Controller → 클라이언트 요청을 처리
//Service → 비즈니스 로직을 처리
//Repository → 데이터베이스 CRUD 처리
//DTO → 클라이언트와 데이터를 주고받을 때 사용하는 객체
//Entity → 실제 데이터베이스 테이블과 매칭되는 객체