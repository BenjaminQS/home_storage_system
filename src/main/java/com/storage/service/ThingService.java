package com.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.storage.dao.ThingDAO;
import com.storage.pojo.Thing;
import com.storage.util.Page4Navigator;

@Service
public class ThingService {
	
	@Autowired
	ThingDAO thingDAO;
	
	public Page4Navigator<Thing> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =thingDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
	
	public List<Thing> list() {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
        return thingDAO.findAll(sort);
	}
	
	public void add(Thing bean) {
		thingDAO.save(bean);
	}
	
	public void delete(int id) {
		thingDAO.delete(id);
	}
	
	public Thing get(int id) {
        Thing c= thingDAO.findOne(id);
        return c;
    }

}
