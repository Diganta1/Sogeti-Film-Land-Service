package com.sogeti.filmLand.controller;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.sogeti.filmLand.dao.AvailableServices;

@Component
public interface AvailableServiceController extends CrudRepository<AvailableServices, String> {

	@Query("SELECT new com.sogeti.filmLand.rest.model.AvailableServicesResponse(s.name, s.availableContent, s.price) FROM AvailableServices s where s.user_name =:userName")
	public List<?> findAvailableServiceByName(@Param("userName") String userName);

}
