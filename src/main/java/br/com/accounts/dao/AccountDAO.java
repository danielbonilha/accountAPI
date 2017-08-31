package br.com.accounts.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.accounts.entity.Account;


@RepositoryRestResource(exported = false)
public interface AccountDAO extends CrudRepository<Account, Long> {

	Account findById(Long id);

}
