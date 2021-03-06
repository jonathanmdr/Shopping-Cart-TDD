package br.com.objective.exercices.domain.service;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import br.com.objective.exercices.api.client.FreightClient;
import br.com.objective.exercices.domain.model.ShoppingCart;

public class CheckoutService {

    private final FreightClient freightClient;
    private final ShoppingCart shoppingCart;
    private final BigDecimal minimumValueForFreeShipping;

    public CheckoutService(FreightClient freightClient, ShoppingCart shoppingCart, BigDecimal minimumValueForFreeShipping) {
        this.freightClient = freightClient;
        this.shoppingCart = shoppingCart;
        this.minimumValueForFreeShipping = minimumValueForFreeShipping;
    }

    public BigDecimal getPurchaseAmount() {
        return shoppingCart.getTotalCartValue().add(getFreightRate());
    }

    private BigDecimal getFreightRate() {
        if (shoppingCart.getTotalCartValue().compareTo(minimumValueForFreeShipping) >= 0) {
            return ZERO;
        }

        return freightClient.getFreightRateByZipCode(shoppingCart.getUser().getZipCode());
    }

}
