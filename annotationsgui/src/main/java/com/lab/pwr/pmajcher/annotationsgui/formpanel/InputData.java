package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class InputData {
	private String input;
	private Field field;
	private Method setter;
	private Method getter;
	
	protected InputData() {
		super();
	}
	
	public String getInput() {
		return input;
	}
	protected void setInput(String input) {
		this.input = input;
	}
	public Field getField() {
		return field;
	}
	protected void setField(Field field) {
		this.field = field;
	}

	public Method getSetter() {
		return setter;
	}

	protected void setSetter(Method setter) {
		this.setter = setter;
	}

	public Method getGetter() {
		return getter;
	}

	protected void setGetter(Method getter) {
		this.getter = getter;
	}
}
