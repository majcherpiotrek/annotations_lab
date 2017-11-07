package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class FormPanel extends JPanel{
	
	private Class<?> modelClass;
	private Object formObject;
	private List<FormSubmitEventListener> listeners; 
	private ActionListener submitButtonOnClickListener;

	private JButton submitButton;
	private List<FieldInputPanel> fieldInputPanelsList;
	private static final Class[] sClasses = {String.class, int.class}; 
	protected static final List<Class> supportedClasses = Arrays.asList(sClasses);
	private JTextArea errorTextArea;
	
	protected FormPanel() {
		super();
		listeners = new LinkedList<>();
	}
	
	
	public Class<?> getModelClass() {
		return modelClass;
	}

	protected void setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
	}

	public Object getFormObject() {
		return formObject;
	}

	protected void setFormObject(Object formObject) {
		this.formObject = formObject;
	}
	
	public synchronized void addFormSubmitEventListener(FormSubmitEventListener listener) {
		listeners.add(listener);
	}
	
	protected void addFormElements(
			JLabel titleLabel, 
			JLabel promptLabel, 
			List<FieldInputPanel> fieldInputPanelsList,
			JButton submitButton) {
		 
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(titleLabel);
		add(promptLabel == null ? new JPanel() : promptLabel);
		
		this.fieldInputPanelsList = fieldInputPanelsList;
		for (FieldInputPanel fieldInputPanel : fieldInputPanelsList) {
			add(fieldInputPanel);
		}
		
		this.submitButton = submitButton;
		this.errorTextArea = new JTextArea(20,20);
		this.errorTextArea.setText("");
		this.submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearErrors();
				try {
					Object object = FormModelClassObjectFactory.createObject(modelClass, getFieldInputDataList());
					Set<ConstraintViolation<Object>> violations = validateObject(object);
			        
					if (!violations.isEmpty()) {
			        	formObject = null;
			        	setViolationErrorMessages(fieldInputPanelsList, violations);
			        } else {
			        	formObject = object; 
			        	fireSubmitFormEvent();
			        }	
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					formObject = null;
					errorTextArea.append("Could not create object: " + e1.getMessage() + "\n");
				}
			}
		});
		add(this.submitButton);
		add(this.errorTextArea);
	}
	
	private void setViolationErrorMessages(List<FieldInputPanel> fieldInputPanelsList,
			Set<ConstraintViolation<Object>> violations) {
		System.out.println("Violations: ");
		System.out.println(violations.toString());
		for (ConstraintViolation<Object> v : violations) {
			String cause = v.getPropertyPath().toString();
			for (FieldInputPanel panel : fieldInputPanelsList) {
				if (panel.getRelatedModelField().getName().equals(cause)) {
					panel.setErrorText(v.getMessage());
				}
			}
		}
	}

	private Set<ConstraintViolation<Object>> validateObject(Object object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(object);
		return violations;
	}
	
	private void clearErrors() {
		for (FieldInputPanel panel : fieldInputPanelsList) {
			panel.setErrorText("");
		}
		errorTextArea.setText("");
	}
	private List<InputData> getFieldInputDataList() {
		List<InputData> inputs = new LinkedList<>();
		for (FieldInputPanel panel : fieldInputPanelsList) {
			InputData inputData = new InputData();
			inputData.setField(panel.getRelatedModelField());
			inputData.setInput(panel.getInputString());
			inputData.setGetter(panel.getGetter());
			inputData.setSetter(panel.getSetter());
			inputs.add(inputData);
		}
		return inputs;
	}
	
	private synchronized void fireSubmitFormEvent() {
		FormSubmitEvent event = new FormSubmitEvent(this);
	    for (FormSubmitEventListener listener : listeners) {
	    	listener.onFormSubmitEvent(event);
	    }
	}
}
