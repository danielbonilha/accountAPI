package br.com.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.accounts.constants.API;
import br.com.accounts.dao.AccountDAO;
import br.com.accounts.entity.Account;
import br.com.accounts.validation.ValidatorUtil;
import br.com.accounts.view.ViewHelper;
import br.com.accounts.view.Views;

@RestController
@RequestMapping(API.ACCOUNTS)
public class AccountController {
	
	Logger logger = LoggerFactory.getLogger(AccountController.class);

	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	ViewHelper viewHelper;
	
	
	@GetMapping(API.LIMITS)
	@JsonView(Views.AccountView.class)
	public ResponseEntity<?> getAll() {
		try {
			return new ResponseEntity<Iterable<Account>>(accountDAO.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao buscar accounts", e);
			Account result = new Account();
			result.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(viewHelper.getErrorView(result), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> postOne(@RequestBody Account newOne) {
		newOne.setId(null);
		
		ValidatorUtil.validateAccount(newOne);
		if (newOne.getErrorMessage() != null) {
			return new ResponseEntity<>(viewHelper.getErrorView(newOne), HttpStatus.BAD_REQUEST);
		}
		
		try {
			newOne = accountDAO.save(newOne);
			return new ResponseEntity<>(viewHelper.getAccountView(newOne), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Erro ao criar account", e);
			newOne.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(viewHelper.getErrorView(newOne), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PatchMapping(API.PATH_ID)
	public ResponseEntity<?> patchOne(@PathVariable("id") long id, @RequestBody Account newOne) {
		Account saved = null;
		try {
			saved = accountDAO.findById(id);
		} catch (Exception e) {
			logger.error("Erro ao buscar account", e);
			Account account = new Account();
			account.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(viewHelper.getErrorView(account), HttpStatus.BAD_REQUEST);
		}
		
		if (saved == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ValidatorUtil.validateAccount(newOne);
		if (newOne.getErrorMessage() != null) {
			return new ResponseEntity<>(viewHelper.getErrorView(newOne), HttpStatus.BAD_REQUEST);
		}
		
		saved.setAvailableCreditLimit(saved.getAvailableCreditLimit() + newOne.getAvailableCreditLimit());
		saved.setAvailableWithdrawalLimit(saved.getAvailableWithdrawalLimit() + newOne.getAvailableWithdrawalLimit());
		
		try {
			saved = accountDAO.save(saved);
			return new ResponseEntity<>(viewHelper.getAccountView(saved), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Erro ao atualizar account", e);
			saved.setErrorMessage(e.getMessage());
			return new ResponseEntity<>(viewHelper.getErrorView(newOne), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
