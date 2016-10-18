package com.castis.c3;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.castis.c3.dao.UserDao;
import com.castis.c3.dto.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	@Autowired
	ApplicationContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("this is 'login' page. {}.", locale);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		List<User> userList = userDao.getAll();
		model.addAttribute(userList);
		return "login";
	}

	
	 // 로그아웃
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.setAttribute("userLoginInfo", null);
        return "redirect:/";
    }
    
    // 로그인 처리
    @RequestMapping(value="loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(User user, HttpSession session, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/");
        UserDao userDao = context.getBean("userDao", UserDao.class);
        User loginUser = userDao.get("김경송");
 
        if (loginUser != null) {
            session.setAttribute("userLoginInfo", loginUser);
        }
        return mav;
    }
}
