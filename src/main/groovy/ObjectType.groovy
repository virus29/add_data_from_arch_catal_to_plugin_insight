import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import javax.xml.ws.http.HTTPException

@Slf4j
class ObjectType {

    int getIdObjectType(Object idObjectScheme) throws HTTPException {

        String pathAllObjectTypes = "/rest/insight/1.0/objectschema/$idObjectScheme/objecttypes/flat/"
        def http = new HTTPBuilder(SaveChangesToPluginIsight.inURI)
        int idObjectType
        http.request(Method.GET, ContentType.JSON) {
            req ->
                uri.path = pathAllObjectTypes
                headers.'Authorization' = AuthorizationParameters.BasicAuthorization
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
                    json.each {
                        if (it.name.equals(SaveChangesToPluginIsight.nameObjectType)) {
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
