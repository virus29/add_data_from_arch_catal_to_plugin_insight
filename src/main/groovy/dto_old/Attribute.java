
package dto_old;

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
public class Attribute {

    @JsonProperty("objectTypeAttributeId")
    private Integer objectTypeAttributeId;
    @JsonProperty("objectAttributeValues")
    private List<ObjectAttributeValue> objectAttributeValues = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("objectTypeAttributeId")
    public Integer getObjectTypeAttributeId() {
        return objectTypeAttributeId;
    }

    @JsonProperty("objectTypeAttributeId")
    public void setObjectTypeAttributeId(Integer objectTypeAttributeId) {
        this.objectTypeAttributeId = objectTypeAttributeId;
    }

    @JsonProperty("objectAttributeValues")
    public List<ObjectAttributeValue> getObjectAttributeValues() {
        return objectAttributeValues;
    }

    @JsonProperty("objectAttributeValues")
    public void setObjectAttributeValues(List<ObjectAttributeValue> objectAttributeValues) {
        this.objectAttributeValues = objectAttributeValues;
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
