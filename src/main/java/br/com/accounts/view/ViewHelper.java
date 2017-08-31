package br.com.accounts.view;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import br.com.accounts.entity.Account;

@Component
public class ViewHelper {
	
	public Object getErrorView(Account object) {
		MappingJacksonValue view = new MappingJacksonValue(object);
		view.setSerializationView(Views.ErrorView.class);
		return view;
	}
	
	public Object getAccountView(Account object) {
		MappingJacksonValue view = new MappingJacksonValue(object);
		view.setSerializationView(Views.AccountView.class);
		return view;
	}

}
