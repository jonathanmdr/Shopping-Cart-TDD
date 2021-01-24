package br.com.objective.exercices.domain.model;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShoppingCart {

    private User user;
    private Set<CartItem> items;

    public void addItem(CartItem cartItem) {
        findCartItemByProduct(cartItem.getProduct())
                .ifPresentOrElse(item -> item.add(cartItem.getAmount()), () -> items.add(cartItem));
    }

    public void removeItem(CartItem cartItem) {
        findCartItemByProduct(cartItem.getProduct())
                .ifPresent(item -> items.remove(item));
    }

    public BigDecimal getTotalCartValue() {
        return items.parallelStream()
                .map(ShoppingCart::evaluateItemValue)
                .reduce(ZERO, BigDecimal::add);
    }

    private Optional<CartItem> findCartItemByProduct(Product product) {
        return items.parallelStream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
    }

    private static BigDecimal evaluateItemValue(CartItem item) {
        return item.getProduct().getValue().multiply(valueOf(item.getAmount()));
    }
}
