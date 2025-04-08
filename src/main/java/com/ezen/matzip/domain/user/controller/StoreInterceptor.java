package com.ezen.matzip.domain.user.controller;

import com.ezen.matzip.domain.restaurant.dto.RestaurantDTO;
import com.ezen.matzip.domain.user.service.BusinessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Component
public class StoreInterceptor implements HandlerInterceptor {

    @Autowired
    private BusinessService businessService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !isRedirect(modelAndView)) {
            // store 도메인 관련 뷰에만 적용
            String viewName = modelAndView.getViewName();
            if (viewName != null && (viewName.startsWith("domain/store/") || viewName.startsWith("domain/restaurant/store_restinfo"))) {
                // 요청에서 Principal 객체 직접 가져오기
                Principal principal = request.getUserPrincipal();

                if (principal != null) {
                    // Principal에서 사용자 이름 가져오기
                    String username = principal.getName();

                    // 레스토랑 정보 찾기
                    RestaurantDTO restaurantDTO = businessService.findRestaurantByUserId(username);
                    if (restaurantDTO != null) {
                        modelAndView.addObject("myRestaurantCode", restaurantDTO.getRestaurantCode());
                    }

                    // 필요한 다른 공통 데이터도 여기서 추가
                }
            }
        }
    }

    private boolean isRedirect(ModelAndView modelAndView) {
        return modelAndView.getViewName() != null && modelAndView.getViewName().startsWith("redirect:");
    }
}