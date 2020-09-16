package br.com.objective.exercices.domain.model;

import br.com.objective.exercices.domain.exception.InvalidAmountException;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {

    private static final String DEFAULT_MESSAGE_EXCEPTION = "Quantity item cannot be less than or equal to zero.";

    @Builder
    public CartItem(Product product) {
        this.product = product;
    }
    
    @EqualsAndHashCode.Include
    private Product product;

    @Setter(AccessLevel.NONE)
    private Integer amount;

    public void add(Integer amount) {
        validateAmount(amount);

        this.amount += amount;
    }

    public void remove(Integer amount) {
        validateAmount(amount);

        if ((this.amount - amount) <= 0) {
            throw new InvalidAmountException(DEFAULT_MESSAGE_EXCEPTION);
        }

        this.amount -= amount;
    }

    private void validateAmount(Integer amount) {
        if (this.amount == null) {
            this.amount = 0;
        }

        if (amount <= 0) {
            throw new InvalidAmountException(DEFAULT_MESSAGE_EXCEPTION);
        }
    }

}
