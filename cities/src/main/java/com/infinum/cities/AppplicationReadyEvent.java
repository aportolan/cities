package com.infinum.cities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.infinum.cities.service.MockData;

@Component
public class AppplicationReadyEvent implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private MockData mockData;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		mockData.mock();

	}

}