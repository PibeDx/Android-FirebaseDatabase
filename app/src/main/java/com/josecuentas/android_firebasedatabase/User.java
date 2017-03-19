package com.josecuentas.android_firebasedatabase;

/**
 * Created by iPibeDx on 19/03/17.
 */

public class User {

  private String name;
  private String lastName;
  private int age;
  private String about;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  @Override public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", lastName='" + lastName + '\'' +
        ", age=" + age +
        ", about='" + about + '\'' +
        '}';
  }
}
