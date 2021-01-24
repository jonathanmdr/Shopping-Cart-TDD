package br.com.objective.exercices.domain.service;

import br.com.objective.exercices.api.client.FreightClient;
import br.com.objective.exercices.domain.model.ShoppingCart;
import br.com.objective.exercices.domain.model.User;

import java.math.BigDecimal;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {

    private CheckoutService subject;

    @Test
    public void getPurchaseAmount_evaluatePurchaseAmountWithFreightRate_returningTwenty() {
        given()
            .withFreightRate()
        .when(subject::getPurchaseAmount)
        .then()
            .assertAmountCharged(valueOf(20))
            .verifyNumberInvocationsOfService(1);
    }

    @Test
    public void getPurchaseAmount_evaluatePurchaseAmountWithoutFreightRate_returningOneHundred() {
        given()
            .withoutFreightRate()
        .when(subject::getPurchaseAmount)
        .then()
            .assertAmountCharged(valueOf(100))
            .verifyNumberInvocationsOfService(0);
    }

    private CheckoutServiceTestDSL given() {
        return new CheckoutServiceTestDSL();
    }

    private class CheckoutServiceTestDSL {
        private final FreightClient freightClient = mock(FreightClient.class);
        private final ShoppingCart shoppingCart = mock(ShoppingCart.class);
        private final String zipCode = "85440000";
        private BigDecimal result;

        private CheckoutServiceTestDSL() {
            User user = mock(User.class);
            BigDecimal minimumValueForFreeShipping = valueOf(100);

            subject = new CheckoutService(freightClient, shoppingCart, minimumValueForFreeShipping);

            Mockito.when(user.getZipCode()).thenReturn(zipCode);
            Mockito.when(shoppingCart.getUser()).thenReturn(user);
        }

        private CheckoutServiceTestDSL withFreightRate() {
            Mockito.when(shoppingCart.getTotalCartValue()).thenReturn(TEN);
            Mockito.when(freightClient.getFreightRateByZipCode(zipCode)).thenReturn(TEN);
            return this;
        }

        private CheckoutServiceTestDSL withoutFreightRate() {
            Mockito.when(shoppingCart.getTotalCartValue()).thenReturn(valueOf(100));
            Mockito.when(freightClient.getFreightRateByZipCode(zipCode)).thenReturn(ZERO);
            return this;
        }

        private CheckoutServiceTestDSL when(Supplier<BigDecimal> supplier) {
            result = supplier.get();
            return this;
        }

        private CheckoutServiceTestDSLAsserter then() {
            return new CheckoutServiceTestDSLAsserter();
        }

        private class CheckoutServiceTestDSLAsserter {
            private CheckoutServiceTestDSLAsserter assertAmountCharged(BigDecimal expected) {
                assertEquals(expected, result);
                return this;
            }

            private void verifyNumberInvocationsOfService(int maxNumberInvocations) {
                verify(freightClient, atMost(maxNumberInvocations)).getFreightRateByZipCode(zipCode);
            }
        }
    }
}
