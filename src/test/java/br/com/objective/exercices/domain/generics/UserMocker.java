package br.com.objective.exercices.domain.generics;

import br.com.objective.exercices.domain.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMocker<T> {

    private final User user;
    private final T dsl;

    public UserMocker<T> name(String name) {
        user.setName(name);
        return this;
    }

    public UserMocker<T> zipCode(String zipCode) {
        user.setZipCode(zipCode);
        return this;
    }

    public T end() {
        return dsl;
    }
}
