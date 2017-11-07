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
	private JLabel errorLabel;
	
	public FieldInputPanel(JLabel inputLabel, JTextField inputField, Field relatedModelField, Method getter, Method setter) {
		super();
		this.inputField = inputField;
		this.relatedModelField = relatedModelField;
		this.setter = setter;
		this.getter = getter;
		JPanel inputPanel = new JPanel(); 
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
		inputPanel.add(inputLabel);
		inputPanel.add(this.inputField);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(inputPanel);
		errorLabel = new JLabel("");
		add(errorLabel);
	}
	
	protected void setErrorText(String err) {
		errorLabel.setText(err);
		repaint();
	}
	protected String getInputString() {
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