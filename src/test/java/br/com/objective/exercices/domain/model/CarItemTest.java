package br.com.objective.exercices.domain.model;

import static br.com.objective.exercices.domain.model.CartItem.DEFAULT_MESSAGE_EXCEPTION;
import static org.junit.Assert.assertEquals;

import java.util.function.IntConsumer;

import org.junit.Test;

import br.com.objective.exercices.domain.exception.InvalidAmountException;
import br.com.objective.exercices.domain.generics.CartItemMocker;

public class CarItemTest {

    private CartItem subject;

    @Test
    public void givenAddTenItems_whenExecuteAddItem_shouldReturnTen() {
        given()
            .item()
            .end()
        .when()
            .execute(subject::add, 10)
        .then()
            .assertAmount(10);

    }

    @Test
    public void givenAddTenItemsAndRemoveFiveItems_whenExecuteRemoveItem_shouldReturnFive() {
        given()
            .item()
            .end()
        .when()
            .execute(subject::add, 10)
        .and()
        .when()
            .execute(subject::remove, 5)
        .then()
            .assertAmount(5);
    }

    @Test
    public void givenAddInvalidAmount_whenExecuteAddItem_shouldReturnInvalidAmountException() {
        given()
            .item()
            .end()
        .when()
            .execute(subject::add, 0)
        .then()
            .assertMessageError(DEFAULT_MESSAGE_EXCEPTION);
    }

    @Test
    public void givenRemoveInvalidAmount_whenExecuteRemoveItem_shouldReturnInvalidAmountException() {
        given()
            .item()
            .end()
        .when()
            .execute(subject::remove, 0)
        .then()
            .assertMessageError(DEFAULT_MESSAGE_EXCEPTION);
    }

    @Test
    public void givenRemoveExcessiveAmount_whenEvaluateAmountCarItem_shouldReturnInvalidAmountException() {
        given()
            .item()
            .end()
        .when()
            .execute(subject::add, 10)
        .and()
        .when()
            .execute(subject::remove, 20)
        .then()
            .assertMessageError(DEFAULT_MESSAGE_EXCEPTION);
    }

    private CarItemTestDSL given() {
        return new CarItemTestDSL();
    }

    private class CarItemTestDSL {
        private CarItemTestDSL() {
            subject = new CartItem();
        }

        private CartItemMocker<CarItemTestDSL> item() {
            return new CartItemMocker<>(subject, this);
        }

        private CartItemTestDSLExecutor when() {
            return new CartItemTestDSLExecutor();
        }

        private class CartItemTestDSLExecutor {
            private String message;

            private CartItemTestDSLExecutor execute(IntConsumer consumer, Integer amount) {
                try {
                    consumer.accept(amount);
                } catch (InvalidAmountException ex) {
                    message = ex.getMessage();
                }
                return this;
            }

            private CarItemTestDSL and() {
                return CarItemTestDSL.this;
            }

            private CarItemTestDSLAsserter then() {
                return new CarItemTestDSLAsserter();
            }

            private class CarItemTestDSLAsserter {
                private void assertAmount(Integer expectedAmount) {
                    assertEquals(expectedAmount, subject.getAmount());
                }

                private void assertMessageError(String expectedMessage) {
                    assertEquals(expectedMessage, message);
                }
            }
        }
    }
}
