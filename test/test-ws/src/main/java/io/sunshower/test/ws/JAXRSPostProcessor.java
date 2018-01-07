package io.sunshower.test.ws;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

public class JAXRSPostProcessor implements BeanPostProcessor {

    private ConfigurableListableBeanFactory configurableBeanFactory;

    public JAXRSPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.configurableBeanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(
            Object bean,
            String beanName
    ) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        this.scanDataAccessAnnotation(bean, beanName);
        return bean;
    }

    protected void scanDataAccessAnnotation(Object bean, String beanName) {
        this.configureFieldInjection(bean);
    }

    private void configureFieldInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback =
                new JAXRSFieldCallback(configurableBeanFactory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
    }

}
