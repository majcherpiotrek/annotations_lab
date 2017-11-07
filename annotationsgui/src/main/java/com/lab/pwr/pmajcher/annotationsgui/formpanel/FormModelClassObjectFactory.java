package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.lab.pwr.pmajcher.annotationsgui.formpanel.InputData;

import annotations.Setter;

public class FormModelClassObjectFactory {
	
	public static Object createObject(Class<?> modelClass, List<InputData> inputs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Object object = modelClass.newInstance();
		
		for (InputData inputData : inputs) {
			Field field = inputData.getField();
			
			if (Modifier.isPublic(field.getModifiers())) {
				field.set(object, inputData.getInput());
			} else {
				Method setter = inputData.getSetter();
				
				if ( setter.getAnnotation(Setter.class).targetClass().equals(String.class) ) {
					setter.invoke(object, inputData.getInput());
				} 
				
				if ( setter.getAnnotation(Setter.class).targetClass().equals(int.class) ) {
					setter.invoke(object, Integer.parseInt(inputData.getInput()) );
				}
			}
		}
		
		return object;
	}
}
