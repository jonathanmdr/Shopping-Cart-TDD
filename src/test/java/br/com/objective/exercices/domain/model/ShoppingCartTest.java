package br.com.objective.exercices.domain.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mockito;

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
            .amount(1)
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
            .amount(1)
            .includeToCart()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
            .amount(1)
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
            .amount(1)
            .includeToCart()
            .item()
                .product()
                    .name("Calendar")
                    .value(BigDecimal.valueOf(15))
                .end()
            .amount(1)
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
            .amount(1)
            .includeToCart()
            .item()
                .product()
                    .name("Calendar")
                    .value(BigDecimal.valueOf(15))
                .end()
            .amount(1)
            .includeToCart()
            .item()
                .product()
                    .name("Agenda")
                    .value(BigDecimal.TEN)
                .end()
            .amount(1)
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
        private final User user = mock(User.class);
        private BigDecimal result;

        private ShoppingCartTestDSL() {
            subject = new ShoppingCart(user, new LinkedHashSet<>());
        }

        private UserMocker user() {
            return new UserMocker();
        }

        private CartItemMocker item() {
            return new CartItemMocker();
        }

        private ShoppingCartTestDSL when(Supplier<BigDecimal> supplier) {
            result = supplier.get();
            return this;
        }

        private ShoppingCartTestDSLAsserter then() {
            return new ShoppingCartTestDSLAsserter();
        }

        private class UserMocker {
            private String name;
            private String zipCode;

            private UserMocker name(String name) {
                this.name = name;
                return this;
            }

            private UserMocker zipCode(String zipCode) {
                this.zipCode = zipCode;
                return this;
            }

            private ShoppingCartTestDSL end() {
                Mockito.when(user.getName()).thenReturn(name);
                Mockito.when(user.getZipCode()).thenReturn(zipCode);
                return ShoppingCartTestDSL.this;
            }
        }

        private class CartItemMocker {
            private final CartItem cartItem = new CartItem();
            private final Product product = new Product();

            private CartItemMocker empty() {
                return this;
            }

            private ProductMocker product() {
                return new ProductMocker();
            }

            private CartItemMocker amount(Integer amount) {
                cartItem.add(amount);
                return this;
            }

            private ShoppingCartTestDSL includeToCart() {
                cartItem.setProduct(product);
                subject.addItem(cartItem);
                return ShoppingCartTestDSL.this;
            }

            private ShoppingCartTestDSL removeToCart() {
                cartItem.setProduct(product);
                subject.removeItem(cartItem);
                return ShoppingCartTestDSL.this;
            }

            private ShoppingCartTestDSL end() {
                return ShoppingCartTestDSL.this;
            }

            private class ProductMocker {
                private ProductMocker name(String name) {
                    product.setName(name);
                    return this;
                }

                private ProductMocker value(BigDecimal value) {
                    product.setValue(value);
                    return this;
                }

                private CartItemMocker end() {
                    return CartItemMocker.this;
                }
            }
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
