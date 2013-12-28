package com.mbcdev.nextluas.location;

import android.location.Criteria;

public class CriteriaHolder {

  public static Criteria FINE_CRITERIA;
  
  static {
    FINE_CRITERIA = new Criteria();
    FINE_CRITERIA.setAccuracy(Criteria.ACCURACY_FINE);
  }
  
}
