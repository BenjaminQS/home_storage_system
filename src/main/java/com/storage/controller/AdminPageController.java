package com.storage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
	@GetMapping(value="/admin")
    public String admin(){
		return "redirect:admin_thing_list";
    }
	@GetMapping(value="/admin_thing_list")
	public String listThing(){
		return "admin/listThing";
	}
}
