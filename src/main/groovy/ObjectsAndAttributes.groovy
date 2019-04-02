

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class ObjectsAndAttributes {
    TransferObject getIdAttributesAndAllObjects(Map authorizationAndURI, String pathAllObjects, String bodyGetAllObjects, Map nameAttributesObjectType, String pathAllIdAttributes) {
        Map<String, String> mapIdAttributesObjectType = new HashMap<String, String>()
        List<String> idObjectsList = new ArrayList<String>()
        def http = new HTTPBuilder(authorizationAndURI.get("inURI"))

        http.request(Method.GET, ContentType.JSON) {
            req ->
                headers.'Authorization' = authorizationAndURI.get("authorization")
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
                    json.each {
                        if (it.name.equals(nameAttributesObjectType.get("nameAttributeIdObjectType"))) {
                            mapIdAttributesObjectType.put("idAttributeIdObjectType", (String) it.id)
                        }
                        if (it.name.equals(nameAttributesObjectType.get("nameAttributeNameObjectType"))) {
                            mapIdAttributesObjectType.put("idAttributeNameObjectType", (String) it.id)
                        }
                        if (it.name.equals(nameAttributesObjectType.get("nameAttributeFullNameObjectType"))) {
                            mapIdAttributesObjectType.put("idAttributeFullNameObjectType", (String) it.id)
                        }
                    }
                }
                response.failure = { resp ->
                    log.error "Request Id Attribute failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    return "Request failed with status " + resp.status
                }
        }

        http.request(Method.POST, ContentType.JSON,) {
            request ->
                uri.path = pathAllObjects
                headers.'Authorization' = authorizationAndURI.get("authorization")
                body = bodyGetAllObjects
                response.success = { resp, json ->
                    json.objectEntries.each {
                        it.attributes.each{
                            if(it.objectAttributeValues.value[0]!=null && (it.objectTypeAttributeId as String).equals(mapIdAttributesObjectType.get("idAttributeIdObjectType"))){
                                idObjectsList.add((String)it.objectAttributeValues.value[0])
                                log.info("attributeValues[0] = "+it.objectAttributeValues.value[0])
                            }
                        }
                    }
                    log.info("idObjectsList = " + idObjectsList)
                    log.info("mapIdAttributesObjectType = " + mapIdAttributesObjectType)
                }
                response.failure = { resp ->
                    log.error "Request Id Object failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    return "Request failed with status " + resp.status
                }
        }
        TransferObject to = new TransferObject()
        to.setIdObjectsList(idObjectsList)
        to.setMapIdAttributesObjectType(mapIdAttributesObjectType)

        log.info("TransferObject = " + to.getMapIdAttributesObjectType())
        log.info("TransferObject = " + to.getIdObjectsList())
        log.info("TransferObject = " + to)
        return to
    }
}
