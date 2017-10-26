package com.aircontrol.demo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;

import com.aircontrol.demo.contentview.ContentView;
import com.aircontrol.demo.contentview.ContentWidget;



public class InjectUtils {
	
	 public static void injectObject(Object object, Activity activity) {

		 Class<?> classType = object.getClass();//返回该对象的运行时类的Java.lang.Class对象（API上的解释），获取一个类的Class对象

		 if (classType.isAnnotationPresent(ContentView.class)) //(括号内的参数对应的是注释类型的Class对象，如果返回为true，则指定类型的注释存在于此元素上，否则返回false)
		 {
			 ContentView annotation = classType.getAnnotation(ContentView.class);
			 try {
					 Method method = classType.getMethod("setContentView", int.class);//反射方法名
					 method.setAccessible(true);//类中的成员变量如果为private则应该执行此操作，也就是说可以忽略权限的限制
					 int resId = annotation.value();
					 method.invoke(object, resId);//反射调用object中的方法，resId为方法中参数的值
				 } catch (NoSuchMethodException e) {
				 e.printStackTrace();
				 } catch (IllegalArgumentException e) {
				 e.printStackTrace();
				 } catch (IllegalAccessException e) {
				 e.printStackTrace();
				 } catch (InvocationTargetException e) {
				 e.printStackTrace();
				 }
	     }
		 
		 Field[] fields = classType.getDeclaredFields();

		 if (null != fields && fields.length > 0) 
		 {
			 for (Field field : fields) 
			 {
				 if (field.isAnnotationPresent(ContentWidget.class)) 
				 {
					 ContentWidget annotation = field.getAnnotation(ContentWidget.class);
					 int viewId = annotation.value();
					 View view = activity.findViewById(viewId);
					 if (null != view) 
					 {
						 try{
							 field.setAccessible(true);
							 field.set(object, view);
						 } catch (IllegalArgumentException e) {
						 e.printStackTrace();
						 } catch (IllegalAccessException e) {
						 e.printStackTrace();
					     }
			        }
		        }


		     }


	    } 
		 
		 
		 
   }

}
