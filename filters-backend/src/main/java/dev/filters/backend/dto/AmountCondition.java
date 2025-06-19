package dev.filters.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class AmountCondition implements FilterCondition {
    
    @Getter
    public enum AmountOperator {
        GT(">"), GTE(">="), EQ("="), LTE("<="), LT("<");
        
        private final String symbol;
        
        AmountOperator(String symbol) {
            this.symbol = symbol;
        }

    }
    
    private final String type = "amount";
    
    @NotNull
    private final AmountOperator operator;
    
    @NotNull
    private final Double value;
    
    @Override
    public String type() {
        return type;
    }
}
