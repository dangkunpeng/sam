package com.sam.sam_parents.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {
	
	@RequestMapping("/403")  
	@ResponseStatus(HttpStatus.FORBIDDEN)  
	public String forbiddenError() {  
	    return "/error/403";  
	} 
	
	@RequestMapping("/404")  
	@ResponseStatus(HttpStatus.NOT_FOUND)  
	public String notFoundError() {  
	    return "/error/404";  
	}  
	  
	@RequestMapping("/500")  
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
	public String interdError() {  
	    return "/error/500";  
	} 
}
