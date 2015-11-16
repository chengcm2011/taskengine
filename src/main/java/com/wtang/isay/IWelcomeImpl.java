package com.wtang.isay;

public class IWelcomeImpl implements IWelcome {

	public String sayHello(String arg1, String arg2) {
		return "msg:" + arg1 +" say hello to "+ arg2;
	}
}