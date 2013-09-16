package com.czy.mybbs;

public class BBSCommand extends Command {

	@Override
	public void list() {
		this.setTemplateName("hello");
		this.templadtContext.put("hello", "hello ok");
	}

	public void action() {
		this.setTemplateName("actionTest");
		this.templadtContext.put("actionTest", "action");
	}

}
