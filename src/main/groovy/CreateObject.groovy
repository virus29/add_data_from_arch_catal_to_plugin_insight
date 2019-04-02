

import groovy.util.logging.Slf4j
import groovyx.net.http.AuthConfig
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import javax.xml.ws.http.HTTPException

@Slf4j
class CreateObject {
    void createObject(Map<String, String> authorizationAndURI, Map<String, String> ntlmAuthorization, Map<String,
        String> idsAndAttribObjType, List<String> idObjects, String pathArchitecturalCatalog, String pathToCreateObject) throws HTTPException {
        def http = new HTTPBuilder(pathArchitecturalCatalog)
        http.ignoreSSLIssues()
        AuthConfig ac = new AuthConfig(http)
        ac.ntlm(ntlmAuthorization.get("username"), ntlmAuthorization.get("password"), ntlmAuthorization.get("workstation"), ntlmAuthorization.get("domain"))
        http.request(Method.GET, ContentType.TEXT) {
            req ->
                response.success = { resp, xml ->
                    String xmlString = xml.text
                    def xmlFile = new XmlParser().parseText(xmlString)
                    xmlFile.each {
                        String valueAttributeIdObjectType
                        it.'**'.'d:Id'[0].each {
                            valueAttributeIdObjectType = it
                        }
                        log.error("idObjects = "+idObjects)
                        log.error("valueAttributeIdObjectType = "+valueAttributeIdObjectType)
                        if (!valueAttributeIdObjectType.equals(null) && !idObjects.contains(valueAttributeIdObjectType)) {
                            String valueAttributeNameObjectType
                            it.'**'.'d:Title'[0].each {
                                String valueAttributeNameObjectTypeOriginal = it as String
                                valueAttributeNameObjectType= valueAttributeNameObjectTypeOriginal.replaceAll(/\s/," ").trim().replaceAll(/[^\w А-Яа-я]/,"")
                            }
                            String valueAttributeFullNameObjectType
                            it.'**'.'d:ITS_DetailName'[0].each {
                                String valueAttributeFullNameObjectTypeOriginal = it as String
                                log.error(valueAttributeFullNameObjectTypeOriginal)
                                valueAttributeFullNameObjectType = valueAttributeFullNameObjectTypeOriginal.replaceAll(/\s/," ").trim().replaceAll(/[^\w А-Яа-я]/,"")
                                log.error(valueAttributeFullNameObjectType)
                            }
                            String idObjectType = idsAndAttribObjType.get("idObjectType")
                            String idAttributeIdObjectType = idsAndAttribObjType.get("idAttributeIdObjectType")
                            String idAttributeNameObjectType = idsAndAttribObjType.get("idAttributeNameObjectType")
                            String idAttributeFullNameObjectType = idsAndAttribObjType.get("idAttributeFullNameObjectType")
                            if (!idObjectType.equals(null) && !idAttributeIdObjectType.equals(null) && !idAttributeNameObjectType.equals(null) && !idAttributeFullNameObjectType.equals(null)) {
                                StringBuilder sb = new StringBuilder()
                                sb.append('{"objectTypeId": ').append(idObjectType).append(',"attributes": [{"objectTypeAttributeId": ')
                                        .append(idAttributeIdObjectType).append(',"objectAttributeValues": [{"value": "')
                                        .append(valueAttributeIdObjectType).append('"}]},{"objectTypeAttributeId": ')
                                        .append(idAttributeNameObjectType).append(',"objectAttributeValues": [{"value": "')
                                        .append(valueAttributeNameObjectType).append('"}]},{"objectTypeAttributeId": ')
                                        .append(idAttributeFullNameObjectType).append(',"objectAttributeValues": [{"value": "')
                                        .append(valueAttributeFullNameObjectType).append('"}]}]}')
                                String createObjJsonString = sb
                                        log.info("createObjJsonString = " + createObjJsonString)
                                new RequestCreateObject().requestCreateObject(authorizationAndURI, pathToCreateObject, createObjJsonString)
                            }
                        }
                    }
                    if (xmlFile.link[0] != null) {
                        pathArchitecturalCatalog = (String) xmlFile.link[0].@'href'
                        createObject(authorizationAndURI, ntlmAuthorization, idsAndAttribObjType, idObjects, pathArchitecturalCatalog, pathToCreateObject)
                    }
                }
                response.failure = { resp ->
                    "Request Create Object failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Response header : "+i+" - "+it)}
                }
        }
    }
}
