package com.xabe.formData.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xabe.formData.persistence.PaginationContext;
import com.xabe.formData.service.ServiceBase;
import com.xabe.formData.model.EntityBase;
import com.xabe.formData.model.ExampleBase;
import com.xabe.formData.service.ServiceBase;

public interface ServiceBase<T extends EntityBase, D extends ExampleBase> {
	public static final Logger LOGGER = LoggerFactory.getLogger(ServiceBase.class);

	void add(T t);

	void update(T t);
	
	void update(T t,D d);

	void delete(T t);
	
	void delete(D d);

	List<T> getAll();
	
	List<T> getAll(D d);
	
	List<T> getPaginated(D example,PaginationContext paginationContext);
}