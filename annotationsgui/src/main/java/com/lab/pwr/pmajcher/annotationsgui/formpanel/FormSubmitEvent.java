package com.lab.pwr.pmajcher.annotationsgui.formpanel;

import java.util.EventObject;

public class FormSubmitEvent extends EventObject{
	
	public FormSubmitEvent(FormPanel source) {
		super(source);
	}
	
	public FormPanel getSource() {
		return (FormPanel) super.getSource();
	}
}
