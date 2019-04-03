import groovyx.net.http.AuthConfig
import groovyx.net.http.HTTPBuilder

class AuthorizationParameters {
    static def BasicAuthorization = "Basic ${"$SaveChangesToPluginIsight.username:$SaveChangesToPluginIsight.password".bytes.encodeBase64().toString()}"

    static HTTPBuilder NtlmAuthorization(HTTPBuilder httpBuilder) {
        AuthConfig ac = new AuthConfig(httpBuilder)
        ac.ntlm(SaveChangesToPluginIsight.username, SaveChangesToPluginIsight.password, SaveChangesToPluginIsight.workstation, SaveChangesToPluginIsight.domain)
    }
}
