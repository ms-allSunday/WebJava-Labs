package com.example.spacecatsmarket.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
public class Cart {
    List<Product> products = new ArrayList<>();
}
