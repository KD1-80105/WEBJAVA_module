package com.sunbeam.dao;
import java.util.Date;

public class DateTimeUtil {

 public static java.util.Date sqlDateToUtilDate(java.sql.Date sDate) {

 java.util.Date uDate = new java.util.Date(sDate.getTime());

 return uDate;

 }

 public static java.sql.Date utilDateToSqlDate(java.util.Date uDate) {

 java.sql.Date sDate = new java.sql.Date(uDate.getTime());

 return sDate;

 }

}





















