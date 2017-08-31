package br.com.accounts.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.accounts.view.Views;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = -2055653908094303633L;

	private Long id;
	private double availableCreditLimit;
	private double availableWithdrawalLimit;
	private String errorMessage;
	
	
	@Id
	@GeneratedValue
	@JsonView(Views.IdView.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonView(Views.AccountView.class)
	public double getAvailableCreditLimit() {
		return availableCreditLimit;
	}

	public void setAvailableCreditLimit(double availableCreditLimit) {
		this.availableCreditLimit = availableCreditLimit;
	}
	
	@JsonView(Views.AccountView.class)
	public double getAvailableWithdrawalLimit() {
		return availableWithdrawalLimit;
	}

	public void setAvailableWithdrawalLimit(double availableWithdrawalLimit) {
		this.availableWithdrawalLimit = availableWithdrawalLimit;
	}
	

	@Transient
	@JsonView(Views.ErrorView.class)
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(availableCreditLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(availableWithdrawalLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Double.doubleToLongBits(availableCreditLimit) != Double.doubleToLongBits(other.availableCreditLimit))
			return false;
		if (Double.doubleToLongBits(availableWithdrawalLimit) != Double
				.doubleToLongBits(other.availableWithdrawalLimit))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", availableCreditLimit=" + availableCreditLimit + ", availableWithdrawalLimit="
				+ availableWithdrawalLimit + "]";
	}

}
