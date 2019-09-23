package com.interview.prototype.util;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ErrorInfo {
    public String url;
    public List<String> messages;
    
    public ErrorInfo(HttpServletRequest req, Exception ex) {
    	this.url = req.getRequestURL().toString();
        this.messages = Arrays.asList(ex.getLocalizedMessage());
    }
    
    public ErrorInfo(HttpServletRequest req, String message) {
    	this.url = req.getRequestURL().toString();
        this.messages = Arrays.asList(message);
    }

	public ErrorInfo(HttpServletRequest req, List<String> messages) {
		this.url = req.getRequestURL().toString();
        this.messages = messages;
	}
}