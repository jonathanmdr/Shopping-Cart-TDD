package br.com.objective.exercices.domain.model;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void constructor_constructUser_returningUserInstance() {
        User userNoArgs = new User();

        Assert.assertNotNull(userNoArgs);
        Assert.assertNull(userNoArgs.getName());
        Assert.assertNull(userNoArgs.getZipCode());
        userNoArgs.setName("Jonathan Henrique");
        userNoArgs.setZipCode("85440000");
        Assert.assertEquals("Jonathan Henrique", userNoArgs.getName());
        Assert.assertEquals("85440000", userNoArgs.getZipCode());

        User userAllArgs = new User("Jonathan Henrique", "85440000");

        Assert.assertNotNull(userAllArgs);
        Assert.assertNotNull(userAllArgs.getName());
        Assert.assertNotNull(userAllArgs.getZipCode());
        Assert.assertEquals("Jonathan Henrique", userAllArgs.getName());
        Assert.assertEquals("85440000", userAllArgs.getZipCode());

        User userBuilder = User.builder()
                .name("Jonathan Henrique")
                .zipCode("85440000")
                .build();

        Assert.assertNotNull(userBuilder);
        Assert.assertNotNull(userBuilder.getName());
        Assert.assertNotNull(userBuilder.getZipCode());
        Assert.assertEquals("Jonathan Henrique", userBuilder.getName());
        Assert.assertEquals("85440000", userBuilder.getZipCode());
    }

}
