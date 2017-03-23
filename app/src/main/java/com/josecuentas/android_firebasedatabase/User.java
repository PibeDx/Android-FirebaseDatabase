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
  private Long createTime;
  private Long updateTime;


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

  public Object getCreateTime() {
    if (createTime == null) return ServerValue.TIMESTAMP;
    return createTime;
  }

  public Map<String, String> getUpdateTime() {
    return ServerValue.TIMESTAMP;
  }

  @Exclude public Long getCreationDateLong() {
    return createTime;
  }
  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  @Exclude public Long getUpdateTimeLong() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
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
