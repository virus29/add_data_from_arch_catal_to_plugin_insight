
package dto.update_object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "attributes"
})
public class JsonUpdateObjectDTO {

    @JsonProperty("attributes")
    private List<UpdateAttributeOfObjectDTO> updateAttributeOfObjectDTOS = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attributes")
    public List<UpdateAttributeOfObjectDTO> getUpdateAttributeOfObjectDTOS() {
        return updateAttributeOfObjectDTOS;
    }

    @JsonProperty("attributes")
    public void setUpdateAttributeOfObjectDTOS(List<UpdateAttributeOfObjectDTO> updateAttributeOfObjectDTOS) {
        this.updateAttributeOfObjectDTOS = updateAttributeOfObjectDTOS;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
