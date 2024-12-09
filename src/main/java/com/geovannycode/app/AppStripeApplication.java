package com.geovannycode.app;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "my-app")
public class AppStripeApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(AppStripeApplication.class, args);
	}

}
