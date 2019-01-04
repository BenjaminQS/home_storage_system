package com.storage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storage.pojo.Thing;
import com.storage.service.ThingService;
import com.storage.util.ImageUtil;
import com.storage.util.Page4Navigator;

@RestController
public class ThingController {
	@Autowired
	ThingService thingService;

	@GetMapping("/things")
	public Page4Navigator<Thing> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
		start = start<0?0:start;
		Page4Navigator<Thing> page =thingService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
		return page;
	}
	
	@PostMapping("/things")
    public Object add(Thing bean, MultipartFile image, HttpServletRequest request) throws Exception {
        thingService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }
    public void saveOrUpdateImageFile(Thing bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }
    
    @DeleteMapping("/things/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        thingService.delete(id);
        File  imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return null;
    }
    
    @GetMapping("/things/{id}")
    public Thing get(@PathVariable("id") int id) throws Exception {
        Thing bean=thingService.get(id);
        return bean;
    }
    
    @PutMapping("/things/{id}")
    public Object update(Thing bean, MultipartFile image,HttpServletRequest request) throws Exception {
    	String name = request.getParameter("name");
    	bean.setName(name);
    	String location = request.getParameter("location");
    	bean.setLocation(location);
    	String time = request.getParameter("time");
    	bean.setTime(time);
    	thingService.update(bean);
    	if(image != null){
    		saveOrUpdateImageFile(bean, image, request);
    	}
    	return bean;
    }
    
}
