package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import annotations.FormInput;
import annotations.FormModel;
import annotations.Getter;
import annotations.Setter;

public class FormPanelFactory {
	
	private static final int COLUMNS = 2;
	private static final int ADDITIONAL_ROWS = 3;

	public static FormPanel createFormBasedOnDataModel(Class<?> dataModel, int inputFieldsWidth) throws InvalidFormDataModelException, FieldNotAccessibleException {
			
		if (!dataModel.isAnnotationPresent(FormModel.class)) {
			throw new InvalidFormDataModelException("Model class has to be annotated with @FormModel annotation");
		}
		FormModel formModelAnnotation = (FormModel) dataModel.getAnnotation(FormModel.class);
		
		List<Field> formFields = Arrays.asList(dataModel.getDeclaredFields())
				.stream()
				.filter(field -> field.isAnnotationPresent(FormInput.class))
				.collect(Collectors.toList());
		
		// ROWS = form inputs + title + prompt + submit button
		// COLUMNS = labels + inputs
		int rows = formFields.size() + ADDITIONAL_ROWS;
		
		JLabel titleLabel = new JLabel(formModelAnnotation.formTitle());
		JLabel promptLabel = null;
		if (!formModelAnnotation.formPrompt().equals("")) {
			promptLabel = new JLabel(formModelAnnotation.formPrompt());
		} 
		
		List<FieldInputPanel> fieldInputPanelsList = new LinkedList<>();
		for (Field field : formFields) {
		
			if (!isSettingFieldValuePossible(dataModel, field)) {
				throw new FieldNotAccessibleException(field.getName());
			}
			Method getter = null;
			Method setter = null;
			if (!Modifier.isPublic(field.getModifiers())) {
				getter = getFieldGetter(dataModel, field);
				setter = getFieldSetter(dataModel, field);
			}
			fieldInputPanelsList.add(createFieldInputPanel(inputFieldsWidth, field, getter, setter));
		}
		
		JButton submitButton = new JButton(formModelAnnotation.formSubmit());
		
		FormPanel formPanel = new FormPanel();
		formPanel.setModelClass(dataModel);
		formPanel.addFormElements(titleLabel, promptLabel, fieldInputPanelsList, submitButton);
		
		return formPanel;
	}

	private static FieldInputPanel createFieldInputPanel(int inputFieldsWidth, Field field, Method getter, Method setter) {
		FormInput formInputAnnotation = (FormInput) field.getAnnotation(FormInput.class);
		JLabel fieldLabel = new JLabel(formInputAnnotation.inputLabel());
		JTextField inputTextField;
		if (formInputAnnotation.isPasswordField()) {
			inputTextField = new JPasswordField(inputFieldsWidth);
		} else {
			inputTextField = new JTextField(inputFieldsWidth);
		}
		return new FieldInputPanel(fieldLabel, inputTextField, field, getter, setter);
	}
	
	private static boolean isSettingFieldValuePossible(Class<?> dataModel, Field field) {
		boolean isAccessible = true;
		if (!Modifier.isPublic(field.getModifiers())) {
			 List<Method> methods = Arrays.asList(dataModel.getMethods())
					 .stream()
					 .filter(m -> m.isAnnotationPresent(Setter.class))
					 .collect(Collectors.toList());
			 
			 boolean setterFound = false;
			 for (Method m : methods) {
				 Setter annotation = (Setter) m.getAnnotation(Setter.class);
				 if (annotation.targetFieldName().equals(field.getName())) {
					 setterFound = true;
					 break;
				 }
			 }
			 isAccessible = setterFound; 
		}
		return isAccessible;
	}
	
	private static Method getFieldGetter(Class<?> dataModel, Field field) {
		List<Method> methods = Arrays.asList(dataModel.getMethods())
				 .stream()
				 .filter(
						 m -> m.isAnnotationPresent(Getter.class) 
						 && m.getAnnotation(Getter.class).targetFieldName().equals(field.getName())
						 )
				 .collect(Collectors.toList());
		return methods.isEmpty() ? null : methods.get(0);
	}
	
	private static Method getFieldSetter(Class<?> dataModel, Field field) {
		List<Method> methods = Arrays.asList(dataModel.getMethods())
				 .stream()
				 .filter(
						 m -> m.isAnnotationPresent(Setter.class)
						 && m.getAnnotation(Setter.class).targetFieldName().equals(field.getName())
						 )
				 .collect(Collectors.toList());
		return methods.isEmpty() ? null : methods.get(0);
	}
}

