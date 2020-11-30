package br.com.objective.exercices.domain.generics;

import java.math.BigDecimal;

import br.com.objective.exercices.domain.model.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductMocker<T> {

    private final Product product;
    private final T dsl;

    public ProductMocker<T> name(String name) {
        product.setName(name);
        return this;
    }

    public ProductMocker<T> value(BigDecimal value) {
        product.setValue(value);
        return this;
    }

    public T end() {
        return dsl;
    }
}
