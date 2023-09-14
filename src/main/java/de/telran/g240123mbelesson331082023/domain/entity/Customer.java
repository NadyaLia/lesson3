package de.telran.g240123mbelesson331082023.domain.entity;

public interface Customer {
    int getId();
    String getName();
    int getAge();
    String getEmail();
    Cart getCart();

}
