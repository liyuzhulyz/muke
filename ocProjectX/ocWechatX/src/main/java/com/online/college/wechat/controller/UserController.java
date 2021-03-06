package com.online.college.wechat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.SessionContext;
import com.online.college.service.core.user.domain.UserCourseSection;
import com.online.college.service.core.user.domain.UserCourseSectionDto;
import com.online.college.service.core.user.service.IUserCourseSectionService;

/**
 *
 * @Description: 用户
 * @author majinlan
 * @date 2018-02-24 10:22
 * @version V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserCourseSectionService userCourseSectionService;

    /**
     * 个人主页
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, TailPage<UserCourseSectionDto> page) {
        ModelAndView mv = new ModelAndView("user");

        // 当前用户id
        Long userId = SessionContext.getWxUserId(request);
        if (null == userId) {
            return new ModelAndView("redirect:/auth/login.html");
        }

        // 获取学习记录
        UserCourseSection queryEntity = new UserCourseSection();
        queryEntity.setUserId(userId);

        page = userCourseSectionService.queryPage(queryEntity, page);
        mv.addObject("page", page);

        // 当前用户
        mv.addObject("curUser", SessionContext.getWxAuthUser(request));
        return mv;
    }

    /**
     * 加载评论
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/userCourseSection")
    public ModelAndView userCourseSection (HttpServletRequest request, TailPage<UserCourseSectionDto> page) {
        ModelAndView modelAndView = index(request, page);
        modelAndView.setViewName("userCourseSection");
        return modelAndView;
    }

}
