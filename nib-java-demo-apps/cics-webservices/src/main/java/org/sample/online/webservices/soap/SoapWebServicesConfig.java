package org.sample.online.webservices.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

@Configuration
@EnableWs
public class SoapWebServicesConfig implements WsConfigurer {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> soap11Dispatcher(ApplicationContext ctx) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        servlet.setMessageFactoryBeanName("soap11MessageFactory");
        var reg = new ServletRegistrationBean<>(servlet, "/ws/soap11/*");
        reg.setName("soap11");
        return reg;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> soap12Dispatcher(ApplicationContext ctx) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        servlet.setMessageFactoryBeanName("soap12MessageFactory");
        var reg = new ServletRegistrationBean<>(servlet, "/ws/soap12/*");
        reg.setName("soap12");
        return reg;
    }

    @Bean("soap11MessageFactory")
    public SaajSoapMessageFactory soap11MessageFactory() {
        var f = new SaajSoapMessageFactory();
        f.setSoapVersion(SoapVersion.SOAP_11);
        return f;
    }

    @Bean("soap12MessageFactory")
    public SaajSoapMessageFactory soap12MessageFactory() {
        var f = new SaajSoapMessageFactory();
        f.setSoapVersion(SoapVersion.SOAP_12);
        return f;
    }

}
