package com.storage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storage.pojo.Thing;

public interface ThingDAO extends JpaRepository<Thing, Integer>{

}
