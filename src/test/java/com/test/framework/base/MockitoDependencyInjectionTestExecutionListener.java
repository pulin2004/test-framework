package com.test.framework.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class MockitoDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {
    private static final Map<String, MockObject> mockObject   = new HashMap<String, MockObject>();
    private static final List<Field>             injectFields = new ArrayList<Field>();
    @Override
    protected void injectDependencies(final TestContext testContext) throws Exception {
		super.injectDependencies(testContext);
        init(testContext);
    }
    protected void injectMock(final TestContext testContext) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();
        for (Field field : injectFields) {
            Object o = beanFactory.getBean(field.getName(), field.getType());
            if (null != o) {
                Method[] methods = o.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        for (Iterator it = mockObject.keySet().iterator(); it.hasNext();) {
                            String key = (String) it.next();
                            if (method.getName().equalsIgnoreCase("set" + key)) {
                                method.invoke(o, mockObject.get(key).getObj());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void init(final TestContext testContext) throws Exception {
        Object bean = testContext.getTestInstance();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation antt : annotations) {
                if (antt instanceof org.mockito.Mock) {
                    // 注入mock实例
                    MockObject obj = new MockObject();
                    obj.setType(field.getType());
                    obj.setObj(Mockito.mock(field.getType()));
                    field.setAccessible(true);
                    field.set(bean, obj.getObj());
                    mockObject.put(field.getName(), obj);
                } else if (antt instanceof Autowired) {
                    // 只对autowire重新注入
                    injectFields.add(field);
                }
            }
        }
        for (Field field : injectFields) {
            field.setAccessible(true);
            Object object = field.get(bean);
            if (object instanceof Proxy) {
                // 如果是代理的话，找到真正的对象
                Class targetClass = AopUtils.getTargetClass(object);
                if (targetClass == null) {
                    // 可能是远程实现
                    return;
                }
                Field[] targetFields = targetClass.getDeclaredFields();
                for (int i = 0; i < targetFields.length; i++) {
                    // 针对每个需要重新注入的字段
                    for (Map.Entry<String, MockObject> entry : mockObject.entrySet()) {
                        // 针对每个mock的字段
                        if (targetFields[i].getName().equals(entry.getKey())) {
                            targetFields[i].setAccessible(true);
                            targetFields[i].set(getTargetObject(object, entry.getValue().getType()),
                                                entry.getValue().getObj());
                        }
                    }
                }
            } else {
                injectMock(testContext);
            }
        }
    }

    protected <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        } else {
            return (T) proxy; // expected to be cglib proxy then, which is simply a specialized class
        }
    }

    public static class MockObject {
        private Object   obj;
        private Class<?> type;

        public MockObject(){
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }
    }

    public static Map<String, MockObject> getMockobject() {
        return mockObject;
    }

    public static List<Field> getInjectfields() {
        return injectFields;
    }
}


