package com.sogeti.filmLand.controller;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sogeti.filmLand.dao.SubscribedServices;

@Component
public interface SubscribedServiceController extends CrudRepository<SubscribedServices, String> {

	@Transactional
	@Modifying
	@Query("UPDATE SubscribedServices SET user_name = :newSubscriber, name = :subscribedName where user_name =:userName and name = :subscribedName")
	public void updateNewSubscriber(@Param("newSubscriber") String newSubscriber,
			@Param("subscribedName") String subscribedName, @Param("userName") String userName);

	@Query("SELECT new com.sogeti.filmLand.rest.model.SubscribedServicesResponse(s.name, s.subscribedContent, s.price, s.subscribedDate) FROM SubscribedServices s where s.user_name =:userName")
	public List<?> findSubScriberByName(@Param("userName") String userName);

}
