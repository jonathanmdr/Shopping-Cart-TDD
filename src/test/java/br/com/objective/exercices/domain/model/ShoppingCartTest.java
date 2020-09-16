package br.com.objective.exercices.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShoppingCartTest {

    private ShoppingCart subject;

    @Test
    public void constructor_constructShoppingCart_returningShoppingCartInstance() {
        ShoppingCart shoppingCart = new ShoppingCart(new User(), new LinkedHashSet<>());
        String shoppingCartString = shoppingCart.toString();

        Assert.assertNotNull(shoppingCart);
        Assert.assertNotNull(shoppingCart.getUser());
        Assert.assertNotNull(shoppingCart.getItems());
        Assert.assertEquals(shoppingCartString, shoppingCart.toString());
    }

    @Test
    public void getTotalCartValue_evaluateEmptyShoppingCart_returningZero() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Set<CartItem> items = Collections.emptySet();

        subject = new ShoppingCart(user, items);

        Assert.assertEquals(BigDecimal.ZERO, subject.getTotalCartValue());
        Assert.assertEquals(0, subject.getItems().size());
    }

    @Test
    public void getTotalCartValue_evaluateShoppingCartWithItems_returningTen() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .build();
        cartItem.add(1);

        subject = new ShoppingCart(user, new LinkedHashSet<>());
        subject.addItem(cartItem);

        Assert.assertEquals(BigDecimal.TEN, subject.getTotalCartValue());
        Assert.assertEquals(1, subject.getItems().size());
    }

    @Test
    public void getTotalCartValue_evaluateShoppingCartWithIdenticalItems_returningTwenty() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product1 = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        Product product2 = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItem1 = CartItem.builder()
                .product(product1)
                .build();
        cartItem1.add(1);

        CartItem cartItem2 = CartItem.builder()
                .product(product2)
                .build();
        cartItem2.add(1);

        subject = new ShoppingCart(user, new LinkedHashSet<>());
        subject.addItem(cartItem1);
        subject.addItem(cartItem2);

        Assert.assertEquals(new BigDecimal(20), subject.getTotalCartValue());
        Assert.assertEquals(1, subject.getItems().size());
    }

    @Test
    public void getTotalCartValue_evaluateShoppingCartWithDifferentItems_returningTwentyFive() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product1 = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        Product product2 = Product.builder()
                .name("Calendar")
                .value(new BigDecimal(15))
                .build();

        CartItem cartItem1 = CartItem.builder()
                .product(product1)
                .build();
        cartItem1.add(1);

        CartItem cartItem2 = CartItem.builder()
                .product(product2)
                .build();
        cartItem2.add(1);

        subject = new ShoppingCart(user, new LinkedHashSet<>());
        subject.addItem(cartItem1);
        subject.addItem(cartItem2);

        Assert.assertEquals(new BigDecimal(25), subject.getTotalCartValue());
        Assert.assertEquals(2, subject.getItems().size());
    }

    @Test
    public void getTotalCartValue_evaluateShoppingCartWithRemovalItem_returningTen() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product1 = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        Product product2 = Product.builder()
                .name("Calendar")
                .value(new BigDecimal(15))
                .build();

        CartItem cartItem1 = CartItem.builder()
                .product(product1)
                .build();
        cartItem1.add(1);

        CartItem cartItem2 = CartItem.builder()
                .product(product2)
                .build();
        cartItem2.add(1);

        subject = new ShoppingCart(user, new LinkedHashSet<>());
        subject.addItem(cartItem1);
        subject.addItem(cartItem2);

        Assert.assertEquals(new BigDecimal(25), subject.getTotalCartValue());
        Assert.assertEquals(2, subject.getItems().size());

        subject.removeItem(cartItem2);

        Assert.assertEquals(BigDecimal.TEN, subject.getTotalCartValue());
        Assert.assertEquals(1, subject.getItems().size());
    }

    @Test
    public void getTotalCartValue_evaluateShoppingCartWithReducingAmountOfItems_returningFifty() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .build();
        cartItem.add(10);

        subject = new ShoppingCart(user, new LinkedHashSet<>());
        subject.addItem(cartItem);

        Assert.assertEquals(Integer.valueOf(10), cartItem.getAmount());
        Assert.assertEquals(new BigDecimal(100), subject.getTotalCartValue());
        Assert.assertEquals(1, subject.getItems().size());

        cartItem.remove(5);

        Assert.assertEquals(Integer.valueOf(5), cartItem.getAmount());
        Assert.assertEquals(new BigDecimal(50), subject.getTotalCartValue());
        Assert.assertEquals(1, subject.getItems().size());
    }

    @Test
    public void addItem_addItemInShoppingCart_evaluateItemsAmount() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .build();
        cartItem.add(10);

        subject = new ShoppingCart(user, new LinkedHashSet<>());

        Assert.assertEquals(0, subject.getItems().size());

        subject.addItem(cartItem);

        Assert.assertEquals(1, subject.getItems().size());
    }

    @Test
    public void removeItem_removeItemOfShoppingCart_evaluateItemsAmount() {
        User user = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .build();
        cartItem.add(10);

        subject = new ShoppingCart(user, new LinkedHashSet<>());

        Assert.assertEquals(0, subject.getItems().size());

        subject.addItem(cartItem);

        Assert.assertEquals(1, subject.getItems().size());

        subject.removeItem(cartItem);

        Assert.assertEquals(0, subject.getItems().size());
    }

}
