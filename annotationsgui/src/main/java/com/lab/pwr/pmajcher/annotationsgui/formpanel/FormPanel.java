package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormPanel extends JPanel{
	
	private Class<?> modelClass;
	private Object formObject;
	private List<FormSubmitEventListener> listeners; 
	private ActionListener submitButtonOnClickListener;

	private JButton submitButton;
	private List<FieldInputPanel> fieldInputPanelsList;
	
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
	
	public List<InputData> getInputData() {
		List<InputData> inputDataList = new LinkedList<>();
		for (FieldInputPanel fieldInputPanel : fieldInputPanelsList) {
			InputData inputData = new InputData();
			inputData.setInput(fieldInputPanel.getInputData());
			inputData.setField(fieldInputPanel.getRelatedModelField());
			inputData.setGetter(fieldInputPanel.getGetter());
			inputData.setSetter(fieldInputPanel.getSetter());
			inputDataList.add(inputData);
		}
		
		return inputDataList;
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
		this.submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireSubmitFormEvent();
			}
		});
		add(this.submitButton);
	}
	
	private synchronized void fireSubmitFormEvent() {
		FormSubmitEvent event = new FormSubmitEvent(this);
	    for (FormSubmitEventListener listener : listeners) {
	    	listener.onFormSubmitEvent(event);
	    }
	}
}
