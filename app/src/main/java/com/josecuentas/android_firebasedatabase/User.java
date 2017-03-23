package com.josecuentas.android_firebasedatabase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iPibeDx on 19/03/17.
 * http://stackoverflow.com/a/41339272/3738212
 */

public class User {

  private String name;
  private String lastName;
  private int age;
  private String about;
  private TimeStamp timestamp = new TimeStamp();

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

  public TimeStamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(TimeStamp timestamp) {
    this.timestamp = timestamp;
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
