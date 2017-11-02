package com.lab.pwr.pmajcher.annotationsgui;

import java.lang.reflect.Constructor;

import annotations.*;
import customclassloader.CustomClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{	
	private static final String CLASSES_ROOT = "../classes/";
	
    public static void main( String[] args )
    {	
    	System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        ClassLoader loader = new CustomClassLoader(CLASSES_ROOT);
        try {
			Class<?> userClass = loader.loadClass("com.lab.pwr.pmajcher.formsmodel.User");
			Object userObject = userClass.newInstance();
			
			if (userClass.isAnnotationPresent(FormModel.class) ) {
				System.out.println("Annotation found!");
			}
			System.out.println("Class loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
