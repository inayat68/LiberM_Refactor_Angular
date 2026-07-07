# CICS Web Services

A project with some example CICS web services built with NIB Java.

## Prerequisites
Add the project classes (or jar) to the spring boot classpath.

## REST Web Service
The REST web service is implemented in the `RestWebServiceController` class. 

POST: `http://localhost:8080/cics/services/wscust01
BODY: 
```json
{
    "id": 50
}
```

# SOAP Web Service
The SOAP web service is implemented in the `AccountInfoEndpoint` class.

POST: `http://localhost:8080/ws/accountInfo
BODY:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ci="http://www.soap.webservices.online.sample.org">
   <soapenv:Header/>
   <soapenv:Body>
      <ci:AccountInfoRequest>
         <ci:id>50</ci:id>
      </ci:AccountInfoRequest>
   </soapenv:Body>
</soapenv:Envelope>
```
