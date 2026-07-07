/**
 * Copyright (c) 2024-2028, N.I.B., www.nib-labs.io
 *
 * @author N.I.B. dev team
 */
package org.sample.online.webservices.rest;

import com.nib.supernaut.SupernautRegion;
import com.nib.supernaut.Task;
import com.nib.supernaut.exceptions.NotauthException;
import com.nib.supernaut.exceptions.OnlineExceptionFactory;
import com.nib.supernaut.resources.sys.WebServicePctEntry;
import com.nib.supernaut.security.User;
import com.nib.supernaut.terminal.BackgroundTerminalHandler;
import com.nib.supernaut.values.StartCode;
import org.sample.online.webservices.AccountInfo;
import org.sample.online.webservices.CustomerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/cics/services")
@CrossOrigin(origins = "*")
public class RestWebServicesController {
    private static Logger log = LoggerFactory.getLogger(RestWebServicesController.class);
    @Autowired
    private SupernautRegion region;

    @PostMapping("/wscust01")
    @ResponseStatus(HttpStatus.OK)
    AccountInfo account(@RequestBody AccountInfo request, Principal principal) {
        log.info("request {}", request);
        log.info("principal {}", principal);

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
        AccountInfo response = new AccountInfo();
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
    }
}
