

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import javax.xml.ws.http.HTTPException

@Slf4j
class RequestCreateObject {
    void requestCreateObject(Map<String, String> authorizationAndURI, String pathToCreateObject, Object createObjJsonString) throws HTTPException {
        def http = new HTTPBuilder((Object) authorizationAndURI.get("inURI"))
        http.request(Method.POST, ContentType.JSON) {
            request ->
                uri.path = pathToCreateObject
                log.info("URI = "+pathToCreateObject)
                headers.'Authorization' = authorizationAndURI.get("authorization")
                //headers.'Accept' = 'application/json;charset=UTF-8'
                body = createObjJsonString
                log.info("body = "+ createObjJsonString)
                response.success = { resp ->
                    log.info("Object has created!!!!")
                    "Object Created!!!"
                }
                response.failure = { resp ->
                    log.error "Request to Create Object failed with status 5 " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    "Request to create object failed with status" + resp.status
                }
        }
    }
}
