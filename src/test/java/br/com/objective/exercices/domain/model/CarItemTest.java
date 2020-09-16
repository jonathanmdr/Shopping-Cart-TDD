package br.com.objective.exercices.domain.model;

import br.com.objective.exercices.domain.exception.InvalidAmountException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class CarItemTest {

    private CartItem subject;

    @Test
    public void constructor_constructCartItem_returningCartItemInstance() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        CartItem cartItemNoArgs = new CartItem();

        Assert.assertNotNull(cartItemNoArgs);
        Assert.assertNull(cartItemNoArgs.getProduct());
        Assert.assertNull(cartItemNoArgs.getAmount());
        cartItemNoArgs.setProduct(product);
        cartItemNoArgs.add(1);
        Assert.assertNotNull(cartItemNoArgs.getProduct());
        Assert.assertNotNull(cartItemNoArgs.getAmount());

        CartItem cartItemAllArgs = new CartItem(product);

        Assert.assertNotNull(cartItemAllArgs);
        Assert.assertNotNull(cartItemAllArgs.getProduct());
        Assert.assertNull(cartItemAllArgs.getAmount());
        cartItemAllArgs.add(1);
        Assert.assertNotNull(cartItemAllArgs.getAmount());

        CartItem cartItemBuilder = CartItem.builder()
                .product(product)
                .build();

        Assert.assertNotNull(cartItemBuilder);
        Assert.assertNotNull(cartItemBuilder.getProduct());
        Assert.assertNull(cartItemBuilder.getAmount());
        cartItemBuilder.add(1);
        Assert.assertNotNull(cartItemBuilder.getAmount());
    }

    @Test
    public void addItem_evaluateAmountCarItem_returningTen() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.add(10);

        Assert.assertEquals(Integer.valueOf(10), subject.getAmount());
    }

    @Test(expected = InvalidAmountException.class)
    public void addItem_evaluateInvalidAmountCarItem_returningInvalidAmountException() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.add(0);
    }

    @Test(expected = InvalidAmountException.class)
    public void addItem_evaluateInvalidAmountCarItemScenario2_returningInvalidAmountException() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.add(-10);
    }

    @Test
    public void removeItem_evaluateAmountCarItem_returningFive() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.add(10);

        Assert.assertEquals(Integer.valueOf(10), subject.getAmount());

        subject.remove(5);

        Assert.assertEquals(Integer.valueOf(5), subject.getAmount());
    }

    @Test(expected = InvalidAmountException.class)
    public void removeItem_evaluateInvalidAmountCarItem_returningInvalidAmountException() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.remove(0);
    }

    @Test(expected = InvalidAmountException.class)
    public void removeItem_evaluateInvalidAmountCarItemScenario2_returningInvalidAmountException() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.remove(-10);
    }

    @Test(expected = InvalidAmountException.class)
    public void removeItem_evaluateInvalidAmountCarItemScenario3_returningInvalidAmountException() {
        Product product = Product.builder()
                .name("Agenda")
                .value(BigDecimal.TEN)
                .build();

        subject = CartItem.builder()
                .product(product)
                .build();

        subject.add(10);

        Assert.assertEquals(Integer.valueOf(10), subject.getAmount());

        subject.remove(20);
    }

}
