
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
    "objectTypeAttributeId",
    "objectAttributeValues"
})
public class UpdateAttributeOfObjectDTO {

    @JsonProperty("objectTypeAttributeId")
    private int objectTypeAttributeId;
    @JsonProperty("objectAttributeValues")
    private List<UpdateObjectAttributeValueDTO> updateObjectAttributeValueDTOS = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("objectTypeAttributeId")
    public int getObjectTypeAttributeId() {
        return objectTypeAttributeId;
    }

    @JsonProperty("objectTypeAttributeId")
    public void setObjectTypeAttributeId(int objectTypeAttributeId) {
        this.objectTypeAttributeId = objectTypeAttributeId;
    }

    @JsonProperty("objectAttributeValues")
    public List<UpdateObjectAttributeValueDTO> getUpdateObjectAttributeValueDTOS() {
        return updateObjectAttributeValueDTOS;
    }

    @JsonProperty("objectAttributeValues")
    public void setUpdateObjectAttributeValueDTOS(List<UpdateObjectAttributeValueDTO> updateObjectAttributeValueDTOS) {
        this.updateObjectAttributeValueDTOS = updateObjectAttributeValueDTOS;
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
