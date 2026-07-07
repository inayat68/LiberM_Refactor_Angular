package org.sample.online.webservices.soap;

import com.nib.supernaut.SupernautRegion;
import com.nib.supernaut.Task;
import com.nib.supernaut.exceptions.NotauthException;
import com.nib.supernaut.exceptions.OnlineExceptionFactory;
import com.nib.supernaut.resources.sys.WebServicePctEntry;
import com.nib.supernaut.security.User;
import com.nib.supernaut.terminal.BackgroundTerminalHandler;
import com.nib.supernaut.values.StartCode;
import org.sample.online.webservices.CustomerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AccountInfoEndpoint {
    private static Logger log = LoggerFactory.getLogger(AccountInfoEndpoint.class);

    @Autowired
    private SupernautRegion region;

    @PayloadRoot(namespace = WsdlConfig.NAMESPACE_URI, localPart = "AccountInfoRequest")
    @ResponsePayload
    public AccountInfoResponse AccountInfo(@RequestPayload AccountInfoRequest request) {
        log.info("SOAP request {}", request);
        log.info("SOAP request id {}", request.getId());

        try {
            //
            // moving data from request to COMMAREA
            //
            CustomerRecord ca = new CustomerRecord();
            ca.allocate();
            ca.custId.setValue(request.getId());

            //
            // executing the desired program
            //
            try {
                BackgroundTerminalHandler bkgTerm = new BackgroundTerminalHandler(region, null);
                User user = null; //region.authenticate(req.getUserid(), req.getPassword());
                Task t = region.newTask(StartCode.Dpl, bkgTerm, new WebServicePctEntry("CXWU", "WSCUST01"))
                        .data(ca.getAddress().getStorage().getBytes(), ca.length());
                if (user != null) {
                    t.user(user);
                }
                var cr = t.run();
                if (!cr.isNormal()) {
                    OnlineExceptionFactory.throwExceptionFor(cr.getResp());
                }
                ca.setBytes(cr.getData(), cr.getLength());
            } catch (NotauthException e) {
                log.error("cics web service not authorized");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
            } catch (Exception e) {
                log.error("cics web service failed: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }

            //
            // moving COMMAREA in the response object
            //
            AccountInfoResponse response = new AccountInfoResponse();
            response.setId(ca.custId.getInt());
            response.setFirstName(ca.custFirstName.getValue().trim());
            response.setMiddleName(ca.custMiddleName.getValue().trim());
            response.setLastName(ca.custLastName.getValue().trim());
            response.setDateOfBirth(ca.custDobYyyyMmDd.getValue().trim());
            response.setAddress(ca.custAddrLine1.getValue().trim());
            response.setState(ca.custAddrStateCd.getValue().trim());
            response.setCountry(ca.custAddrCountryCd.getValue().trim());
            response.setActive(ca.custPriCardHolderInd.getValue().trim().equals("Y"));
            return response;
        } catch (NotauthException e) {
            log.error("cics web service not authorized");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (Exception e) {
            log.error("cics web service failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
