package br.com.objective.exercices.api.client;

import java.math.BigDecimal;

@FunctionalInterface
public interface FreightClient {

    BigDecimal getFreightRateByZipCode(String zipCode);

}
