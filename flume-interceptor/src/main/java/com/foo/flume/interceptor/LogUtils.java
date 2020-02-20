package com.foo.flume.interceptor;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private static Logger logger = LoggerFactory.getLogger(LogUtils.class);
    public static boolean validateReportLog(String body) {
      try {
          String[] arr = body.split("\\|");
          if (arr.length<2){
              return false;
          }
          if(arr[0].length()!=13||!NumberUtils.isDigits(arr[0])){
              return false;
          }
          if (!arr[1].trim().startsWith("{") || !arr[1].trim().endsWith("}")){
              return false;
          }

          return true;
      }catch (Exception e){
          logger.error("parse Error!message is"+body);
          logger.error(e.getMessage());
          return false;
      }
    }
}
