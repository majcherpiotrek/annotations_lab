package com.lab.pwr.pmajcher.formsmodel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import annotations.AgeField;
import annotations.EmailField;
import annotations.FormModel;
import annotations.NameField;
import annotations.PasswordField;
import annotations.Setter;
import annotations.SurnameField;

@FormModel(
        formTitle = "Registration",
        formPrompt = "Please fill in the form to register new account.",
        formSubmit = "Send"
)
public class User {
	
	public User() {
		super();
	}
	
    @NameField
    @NotNull
    private String name;

    @SurnameField
    @NotNull
    private String surname;

    @PasswordField
    @NotNull
    private String password;

    @EmailField
    @NotNull
    @Email
    private String email;

    @AgeField
    private int age;

    public String getName() {
        return name;
    }

    @Setter(targetFieldName = "name", targetClass = String.class)
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    @Setter(targetFieldName = "surname", targetClass = String.class)
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    @Setter(targetFieldName = "password", targetClass = String.class)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @Setter(targetFieldName = "email", targetClass = String.class)
    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    @Setter(targetFieldName = "age", targetClass = int.class)
    public void setAge(int age) {
        this.age = age;
    }
}
