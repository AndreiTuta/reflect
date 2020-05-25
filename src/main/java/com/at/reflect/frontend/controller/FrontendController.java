package com.at.reflect.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author at
 */
@Controller
public class FrontendController {
	@RequestMapping(value = "/api/frontend/v1//index")
	@ResponseBody
	public ModelAndView welcome() {
		ModelAndView indexView = new ModelAndView("index");
		return indexView;
	}
}
