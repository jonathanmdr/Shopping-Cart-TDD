package br.com.objective.exercices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
public class ShoppingCart {

    private User user;
    private Set<CartItem> items;

    public void addItem(CartItem cartItem) {
        Optional<CartItem> existentItem = findCartItemByProduct(cartItem.getProduct());

        existentItem.ifPresentOrElse(
                (item) -> item.add(cartItem.getAmount()),
                () -> items.add(cartItem)
        );
    }

    public void removeItem(CartItem cartItem) {
        Optional<CartItem> existentItem = findCartItemByProduct(cartItem.getProduct());

        existentItem.ifPresent((item) -> items.remove(item));
    }

    public BigDecimal getTotalCartValue() {
        return items.parallelStream()
                .map(item -> item.getProduct().getValue().multiply(BigDecimal.valueOf(item.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Optional<CartItem> findCartItemByProduct(Product product) {
        return items.parallelStream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
    }

}
