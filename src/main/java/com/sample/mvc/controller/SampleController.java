package com.sample.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sample.mvc.bean.SampleBean;
import com.sample.mvc.service.SampleService;

/**
 * 样例
 * @author lin.pu
 *
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	private SampleService sampleService;
	 @RequestMapping("/{id}")
	    public ModelAndView view(@PathVariable("id") Long id, HttpServletRequest req) {
		 	SampleBean bean = sampleService.findById(id);
	        ModelAndView mv = new ModelAndView();
	        mv.addObject("bean", bean);
	        mv.setViewName("user/view");
	        return mv;
	    }
	 
	 	@RequestMapping("/{pagesize}/{pagenum}")
		public List<SampleBean> views(@PathVariable("pagesize") Integer pagesize,@PathVariable("pagesize") Integer pagenum, HttpServletRequest req) {
			 List<SampleBean> beans = sampleService.queryforPage( pagesize, pagenum);  
		      return beans;
		 }
}
