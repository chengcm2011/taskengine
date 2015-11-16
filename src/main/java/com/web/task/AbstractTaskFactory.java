package com.web.task;


import com.web.task.itf.ITaskCentry;


public abstract class AbstractTaskFactory implements ITaskCentry {
	
	private static final String className = DefaultTaskFactory.class.getName();
	
	private static AbstractTaskFactory factory = null;
	
	private static Object lock = new Object();
	
	public static AbstractTaskFactory getFactory() {
		if(factory == null) {
			synchronized(lock) {
				if(factory == null) {
					try {
						Class<?> clazz = Class.forName(className);
						factory = (AbstractTaskFactory) clazz.newInstance();
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return factory;
	}

}
