package com.wangzhixuan.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 手动从Spring WebApplicationContext 容器中获得bean
 */
public class BeanUtils implements ApplicationContextAware {
 
 private static ApplicationContext applicationContext;
 
 public static Object getBean(String beanName) {
  return applicationContext.getBean(beanName);
 }
 
 public static <T> T getBean(String beanName, Class<T> clazs) {
  return clazs.cast(getBean(beanName));
 }

 public void setApplicationContext(ApplicationContext applicationContext)
   throws BeansException {
  BeanUtils.applicationContext = applicationContext;
 } 
}