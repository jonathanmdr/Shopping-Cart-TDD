package br.com.objective.exercices.domain.generics;

import br.com.objective.exercices.domain.model.CartItem;
import br.com.objective.exercices.domain.model.Product;

public class CartItemMocker<T> {

    private final CartItem cartItem;
    private final T dsl;
    
    private final Product product;

    public CartItemMocker(CartItem cartItem, T dsl) {
        this.cartItem = cartItem;
        this.dsl = dsl;
        this.product = new Product();
        definingProductItem();
    }

    public CartItemMocker<T> empty() {
        return this;
    }

    public ProductMocker<CartItemMocker<T>> product() {
        return new ProductMocker<>(product, this);
    }

    public CartItemMocker<T> add(Integer amount) {
        cartItem.add(amount);
        return this;
    }

    public CartItemMocker<T> remove(Integer amount) {
        cartItem.remove(amount);
        return this;
    }

    public T end() {
        return dsl;
    }

    private void definingProductItem() {
        this.cartItem.setProduct(product);
    }
}
