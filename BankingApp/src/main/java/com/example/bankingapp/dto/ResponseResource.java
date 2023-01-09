package com.example.bankingapp.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Custom Response Bean
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResource<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private String status;
	
	private T data;
	
	private String ticket;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public ResponseResource(String message, String status, T data, String ticket) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
		this.ticket = ticket;
	}	

}
