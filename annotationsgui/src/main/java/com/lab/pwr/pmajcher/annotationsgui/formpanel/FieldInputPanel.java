package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FieldInputPanel extends JPanel{

	private JTextField inputField;
	private Field relatedModelField;
	private Method setter;
	private Method getter;
	
	public FieldInputPanel(JLabel inputLabel, JTextField inputField, Field relatedModelField, Method getter, Method setter) {
		super();
		this.inputField = inputField;
		this.relatedModelField = relatedModelField;
		this.setter = setter;
		this.getter = getter;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(inputLabel);
		add(this.inputField);
	}
	
	protected String getInputData() {
		return inputField.getText();	
	}
	
	protected Field getRelatedModelField() {
		return this.relatedModelField;
	}
	
	protected void clearInputField() {
		this.inputField.setText("");
	}

	protected Method getSetter() {
		return setter;
	}

	protected Method getGetter() {
		return getter;
	}
}