package com.xabe.formData.persistence;

import java.util.List;

import com.xabe.formData.model.EntityBase;
import com.xabe.formData.model.ExampleBase;

public interface PersistenceBase <T extends EntityBase, D extends ExampleBase> {

	void deleteAllData();
	
	List<T> selectByExamplePagination(D example);
}