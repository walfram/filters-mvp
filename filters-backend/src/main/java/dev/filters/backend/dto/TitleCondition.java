package dev.filters.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class TitleCondition implements FilterCondition {
    
    @Getter
    public enum TitleOperator {
        STARTS_WITH("startsWith"),
        ENDS_WITH("endsWith"),
        CONTAINS("contains"),
        EQUALS("equals");
        
        private final String value;
        
        TitleOperator(String value) {
            this.value = value;
        }

    }
    
    private final String type = "title";
    
    @NotNull
    private final TitleOperator operator;
    
    @NotBlank(message = "Value is required")
    private final String value;
    
    @Override
    public String type() {
        return type;
    }
}
