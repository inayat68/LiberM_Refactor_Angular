package org.sample.online.webservices.soap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class WsdlConfig {
    public static final String NAMESPACE_URI = "http://www.soap.webservices.online.sample.org";

    @Bean(name = "accountInfo")
    public DefaultWsdl11Definition accountInfoWsdl11Definition(@Qualifier("accountInfoSchema") XsdSchema accountInfoSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("AccountInfoPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace(NAMESPACE_URI);
        wsdl.setSchema(accountInfoSchema);
        wsdl.setCreateSoap11Binding(true);
        wsdl.setCreateSoap12Binding(true);
        return wsdl;
    }

    @Bean(name = "np100001")
    public DefaultWsdl11Definition np100001Wsdl11Definition(@Qualifier("np100001Schema") XsdSchema np100001Schema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("NP100001Port");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace(NAMESPACE_URI);
        wsdl.setSchema(np100001Schema);
        wsdl.setCreateSoap11Binding(true);
        wsdl.setCreateSoap12Binding(true);
        return wsdl;
    }

    @Bean(name = "accountInfoSchema")
    public XsdSchema accountInfoSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/AccountInfo.xsd"));
    }

    @Bean(name = "np100001Schema")
    public XsdSchema np100001Schema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/NP100001.xsd"));
    }
}
