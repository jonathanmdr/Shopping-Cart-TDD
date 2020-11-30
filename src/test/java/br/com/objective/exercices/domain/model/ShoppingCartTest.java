package br.com.objective.exercices.domain.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

import org.junit.Test;

import br.com.objective.exercices.domain.generics.CartItemMocker;
import br.com.objective.exercices.domain.generics.UserMocker;

public class ShoppingCartTest {

    private ShoppingCart subject;

    @Test
    public void givenEmptyShoppingCart_whenExecuteGetTotalCartValue_shouldReturnZero() {
        given()
            .item()
                .empty()
            .end()
        .when(subject::getTotalCartValue)
        .then()
            .assertTotalCartValue(BigDecimal.ZERO)
            .assertTotalItems(0);
    }

    @Test
    public void givenShoppingCartWithItems_whenExecuteGetTotalCartValues_shouldReturnTen() {
        given()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .includeToCart()
        .when(subject::getTotalCartValue)
        .then()
            .assertTotalCartValue(BigDecimal.TEN)
            .assertTotalItems(1);
    }

    @Test
    public void givenShoppingCartWithIdenticalItems_whenGetTotalCartValue_shouldReturnTwenty() {
        given()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .includeToCart()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .includeToCart()
        .when(subject::getTotalCartValue)
        .then()
            .assertTotalCartValue(BigDecimal.valueOf(20))
           .assertTotalItems(1);
    }

    @Test
    public void givenShoppingCartWithDifferentItems_whenGetTotalCartValue_shouldReturnTwentyFive() {
        given()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .includeToCart()
            .item()
                .product()
                    .name("Calendar")
                    .value(BigDecimal.valueOf(15))
                .end()
                .add(1)
            .end()
            .includeToCart()
        .when(subject::getTotalCartValue)
        .then()
            .assertTotalCartValue(BigDecimal.valueOf(25))
            .assertTotalItems(2);
    }

    @Test
    public void givenShoppingCartAddAndRemoveItem_whenGetTotalCartValue_shouldReturnFifteen() {
        given()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .includeToCart()
            .item()
                .product()
                    .name("Calendar")
                    .value(BigDecimal.valueOf(15))
                .end()
                .add(1)
            .end()
            .includeToCart()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
                .add(1)
            .end()
            .removeToCart()
        .when(subject::getTotalCartValue)
        .then()
            .assertTotalCartValue(BigDecimal.valueOf(15))
            .assertTotalItems(1);
    }

    private ShoppingCartTestDSL given() {
        return new ShoppingCartTestDSL()
                .user()
                    .name("Jonathan Henrique")
                    .zipCode("85440000")
                .end();
    }

    private class ShoppingCartTestDSL {
        private final User user = new User();
        private CartItem cartItem;
        private BigDecimal result;

        private ShoppingCartTestDSL() {
            subject = new ShoppingCart(user, new LinkedHashSet<>());
        }

        private UserMocker<ShoppingCartTestDSL> user() {
            return new UserMocker<>(user, this);
        }

        private CartItemMocker<ShoppingCartTestDSL> item() {
            cartItem = new CartItem();
            return new CartItemMocker<>(cartItem, this);
        }

        private ShoppingCartTestDSL includeToCart() {
            subject.addItem(cartItem);
            return this;
        }

        private ShoppingCartTestDSL removeToCart() {
            subject.removeItem(cartItem);
            return this;
        }

        private ShoppingCartTestDSL when(Supplier<BigDecimal> supplier) {
            result = supplier.get();
            return this;
        }

        private ShoppingCartTestDSLAsserter then() {
            return new ShoppingCartTestDSLAsserter();
        }

        private class ShoppingCartTestDSLAsserter {
            private ShoppingCartTestDSLAsserter assertTotalCartValue(BigDecimal expected) {
                assertEquals(expected, result);
                return this;
            }

            private void assertTotalItems(int expected) {
                assertEquals(expected, subject.getItems().size());
            }
        }
    }
}
