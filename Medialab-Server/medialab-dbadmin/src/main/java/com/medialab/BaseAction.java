package com.medialab;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport
{
	private static final long serialVersionUID = 4308409408364716033L;
	
	public static final String FORM = "form";
	
    public String execute() throws Exception {
        return SUCCESS;
    }
}
