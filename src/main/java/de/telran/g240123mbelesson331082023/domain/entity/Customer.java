package de.telran.g240123mbelesson331082023.domain.entity;

import de.telran.g240123mbelesson331082023.domain.entity.Cart;

public interface Customer {
    int getId();
    String getName();
    Cart getCart();
}
