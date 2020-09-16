package br.com.objective.exercices.domain.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    public void constructor_constructProduct_returningProductInstance() {
        Product productNoArgs = new Product();

        Assert.assertNotNull(productNoArgs);
        Assert.assertNull(productNoArgs.getName());
        Assert.assertNull(productNoArgs.getValue());
        productNoArgs.setName("Cup Personalized");
        productNoArgs.setValue(BigDecimal.TEN);
        Assert.assertEquals("Cup Personalized", productNoArgs.getName());
        Assert.assertEquals(BigDecimal.TEN, productNoArgs.getValue());

        Product productAllArgs = new Product("Cup Personalized", BigDecimal.TEN);

        Assert.assertNotNull(productAllArgs);
        Assert.assertNotNull(productAllArgs.getName());
        Assert.assertNotNull(productAllArgs.getValue());
        Assert.assertEquals("Cup Personalized", productAllArgs.getName());
        Assert.assertEquals(BigDecimal.TEN, productAllArgs.getValue());

        Product productBuilder = Product.builder()
                .name("Cup Personalized")
                .value(BigDecimal.TEN)
                .build();

        Assert.assertNotNull(productBuilder);
        Assert.assertNotNull(productBuilder.getName());
        Assert.assertNotNull(productBuilder.getValue());
        Assert.assertEquals("Cup Personalized", productBuilder.getName());
        Assert.assertEquals(BigDecimal.TEN, productBuilder.getValue());
    }

}
