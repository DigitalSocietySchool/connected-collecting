package com.medialab;

public class TeamAction extends BaseAction
{
	private static final long serialVersionUID = 5523298792370258634L;

	public TeamAction()
	{
		super();
	}

	public String execute() throws Exception
	{
		return SUCCESS;
	}

	public String add()
	{
		return FORM;
	}
}
