package de.telran.g240123mbelesson331082023.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonProduct implements Product {
    private int id;
    private String name;
    private double price;

}
