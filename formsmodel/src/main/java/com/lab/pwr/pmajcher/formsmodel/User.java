package com.lab.pwr.pmajcher.formsmodel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import annotations.FormInput;
import annotations.FormModel;
import annotations.Getter;
import annotations.Setter;

@FormModel(
        formTitle = "Registration",
        formPrompt = "Please fill in the form to register new account.",
        formSubmit = "Send"
)
public class User {
	
	public User() {
		super();
	}
	
	@FormInput(inputLabel = "Name")
    @NotEmpty
    private String name;

	@FormInput(inputLabel = "Surname")
    @NotEmpty
    private String surname;

	@FormInput(inputLabel = "Password", isPasswordField = true)
    @NotEmpty
    @Length(min = 8, max = 50)
    private String password;

	@FormInput(inputLabel = "Email")
    @NotEmpty
    @Email
    private String email;

	@FormInput(inputLabel = "Age")
    private int age;

	@Getter(targetFieldName = "name", targetClass = String.class)
    public String getName() {
        return name;
    }

    @Setter(targetFieldName = "name", targetClass = String.class)
    public void setName(String name) {
        this.name = name;
    }
    
    @Getter(targetFieldName = "surname", targetClass = String.class)
    public String getSurname() {
        return surname;
    }

    @Setter(targetFieldName = "surname", targetClass = String.class)
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    @Getter(targetFieldName = "password", targetClass = String.class)
    public String getPassword() {
        return password;
    }

    @Setter(targetFieldName = "password", targetClass = String.class)
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Getter(targetFieldName = "email", targetClass = String.class)
    public String getEmail() {
        return email;
    }

    @Setter(targetFieldName = "email", targetClass = String.class)
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Getter(targetFieldName = "age", targetClass = int.class)
    public int getAge() {
        return age;
    }

    @Setter(targetFieldName = "age", targetClass = int.class)
    public void setAge(int age) {
        this.age = age;
    }
}
