package com.lab.pwr.pmajcher.annotationsgui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lab.pwr.pmajcher.annotationsgui.formpanel.FieldNotAccessibleException;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.FormModelClassObjectFactory;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.FormPanel;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.FormPanelFactory;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.FormSubmitEvent;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.FormSubmitEventListener;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.InputData;
import com.lab.pwr.pmajcher.annotationsgui.formpanel.InvalidFormDataModelException;

import customclassloader.CustomClassLoader;

/**
 * Hello world!
 *
 */
public class App 
{	
	private static final String CLASSES_ROOT = "../classes/";
	private static final String APP_NAME = "Annotation based form";
	
    public static void main( String[] args )
    {	
    	System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        ClassLoader loader = new CustomClassLoader(CLASSES_ROOT);
        try {
			Class<?> userClass = loader.loadClass("com.lab.pwr.pmajcher.formsmodel.User");
			createAndShowGUI(userClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormDataModelException e) {
			e.printStackTrace();
		} catch (FieldNotAccessibleException e) {
			e.printStackTrace();
		}
    }
    
    private static void createAndShowGUI(Class<?> modelClass) throws InvalidFormDataModelException, FieldNotAccessibleException {
    	JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame mainFrame = new JFrame(APP_NAME);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FormPanel mainPane = FormPanelFactory.createFormBasedOnDataModel(modelClass, 20);
		mainPane.addFormSubmitEventListener(new FormSubmitEventListener() {
			
			@Override
			public void onFormSubmitEvent(FormSubmitEvent e) {
				List<InputData> inputs = e.getSource().getInputData();
		
				try {
					Object obj = FormModelClassObjectFactory.createObject(modelClass, inputs);
					System.out.println("Object created");
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {						// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mainFrame.getContentPane().add(mainPane);
	    mainFrame.pack();
	    mainFrame.setVisible(true);
    }
}
