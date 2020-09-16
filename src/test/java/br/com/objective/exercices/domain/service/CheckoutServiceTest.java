package br.com.objective.exercices.domain.service;

import br.com.objective.exercices.api.client.FreightClient;
import br.com.objective.exercices.domain.model.ShoppingCart;
import br.com.objective.exercices.domain.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {

    private CheckoutService subject;

    @Test
    public void getPurchaseAmount_evaluatePurchaseAmountWithFreightRate_returningTwenty() {
        FreightClient freightClient = mock(FreightClient.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        User user = mock(User.class);

        String zipCode = "85440000";

        subject = new CheckoutService(freightClient, shoppingCart, new BigDecimal(100));

        when(user.getZipCode()).thenReturn(zipCode);
        when(shoppingCart.getUser()).thenReturn(user);
        when(shoppingCart.getTotalCartValue()).thenReturn(BigDecimal.TEN);
        when(freightClient.getFreightRateByZipCode(zipCode)).thenReturn(BigDecimal.TEN);

        BigDecimal purchaseAmountWithFreightRate = subject.getPurchaseAmount();

        verify(freightClient, atMost(1)).getFreightRateByZipCode(zipCode);

        Assert.assertEquals(BigDecimal.valueOf(20), purchaseAmountWithFreightRate);
    }

    @Test
    public void getPurchaseAmount_evaluatePurchaseAmountWithoutFreightRate_returningOneHundred() {
        FreightClient freightClient = mock(FreightClient.class);
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        User user = mock(User.class);

        String zipCode = "85440000";

        subject = new CheckoutService(freightClient, shoppingCart, new BigDecimal(100));

        when(user.getZipCode()).thenReturn(zipCode);
        when(shoppingCart.getUser()).thenReturn(user);
        when(shoppingCart.getTotalCartValue()).thenReturn(BigDecimal.valueOf(100));
        when(freightClient.getFreightRateByZipCode(zipCode)).thenReturn(BigDecimal.ZERO);

        BigDecimal purchaseAmountWithoutFreightRate = subject.getPurchaseAmount();

        verify(freightClient, atMost(0)).getFreightRateByZipCode(zipCode);

        Assert.assertEquals(BigDecimal.valueOf(100), purchaseAmountWithoutFreightRate);
    }

}
