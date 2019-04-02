import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import javax.xml.ws.http.HTTPException

@Slf4j
class ObjectTypes {
    String getIdObjectType(Map authorizationAndURI, String pathAllObjectTypes, String nameObjectType) throws HTTPException {
        def http = new HTTPBuilder((Object) authorizationAndURI.get("inURI"))
        String idObjectType
        http.request(Method.GET, ContentType.JSON) {
            req ->
                uri.path = pathAllObjectTypes
                headers.'Authorization' = authorizationAndURI.get("authorization")
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
//                    String jsonString = json.text
//                    def slurper = new JsonSlurper()
//                    def doc = slurper.parseText(jsonString)
                    json.each {
                        if (it.name.equals(nameObjectType)) {
                            idObjectType = it.id
                            log.info("idObjectType = " + idObjectType)
                        }
                    }
                }
                response.failure = { resp ->
                    log.error "Request Object Type failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    log.error("idObjectType is failed")
                    "Request failed with status" + resp.status
                }
        }
        return idObjectType
    }
}
