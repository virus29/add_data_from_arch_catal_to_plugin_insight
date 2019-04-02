import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class ObjectScheme {
    String getIdObjectScheme(Map authorizationAndURI, String pathAllObjectSchemas, String nameObjectScheme) {

        def http = new HTTPBuilder((Object) authorizationAndURI.get("inURI"))
        String idObjectScheme
        http.request(Method.GET, ContentType.JSON) {
            req ->
                uri.path = pathAllObjectSchemas
                //requestContetntType = ContentType.JSON;
                headers.'Authorization' = authorizationAndURI.get("authorization")
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
//                    String jsonString = json.text
//                    def slurper = new JsonSlurper()
//                    def doc = slurper.parseText(jsonString)
                    json.objectschemas.each {
                        if (it.name.equals(nameObjectScheme)) {
                            idObjectScheme = it.id
                            log.info("idObjectScheme = " + idObjectScheme)
                        }
                    }
                }
                response.failure = { resp ->
                    log.error "Request Object Scheme failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    log.error "idObjectScheme is failed"
                    return "Request failed with status" + resp.status
                }
        }
        return idObjectScheme
    }
}
