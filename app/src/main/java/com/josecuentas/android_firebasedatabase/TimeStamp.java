package com.josecuentas.android_firebasedatabase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by iPibeDx on 23/03/17.
 */

public class TimeStamp {
  private Long createTime;
  private Long updateTime;

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
}
