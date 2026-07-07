package org.sample.online.webservices.soap;

import com.nib.supernaut.SupernautRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDateTime;

@Endpoint
public class NP100001Endpoint {
    private static Logger log = LoggerFactory.getLogger(NP100001Endpoint.class);

    @Autowired
    private SupernautRegion region;

    @PayloadRoot(namespace = WsdlConfig.NAMESPACE_URI, localPart = "NP100001Request")
    @ResponsePayload
    public NP100001Response handle(@RequestPayload NP100001Request request) {

        log.info("SOAP request {}", request);
        var np=new NP100001Response();

        np.w02ENp100001Out=new NP100001Response.W02ENp100001Out();
        np.w02ENp100001Out.enteteUsername="Sorgi Petrillo!"+ LocalDateTime.now();
        return np;

    }
}

