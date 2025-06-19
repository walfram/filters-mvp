package dev.filters.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
public class DateCondition implements FilterCondition {
    
    @Getter
    public enum DateOperator {
        BEFORE("before"),
        AFTER("after"),
        ON("on");
        
        private final String value;
        
        DateOperator(String value) {
            this.value = value;
        }

    }
    
    private final String type = "date";
    
    @NotNull
    private final DateOperator operator;
    
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*", message = "Invalid ISO date string")
    private final String value;
    
    @Override
    public String type() {
        return type;
    }
}
