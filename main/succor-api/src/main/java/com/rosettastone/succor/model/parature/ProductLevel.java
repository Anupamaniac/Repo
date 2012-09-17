package com.rosettastone.succor.model.parature;

public enum ProductLevel implements Identifiable {

    S3("product.level.S3"),
    L2("product.level.L2"),
    B45("product.level.B45"),
    L1("product.level.L1"),
    NA("product.level.NA"),
    S5("product.level.S5"),
    L5("product.level.L5"),
    U1("product.level.U1"),
    S2("product.level.S2"),
    L4("product.level.L4"),
    TOT("product.level.TOT"),
    L3("product.level.L3");

    private final String key;

    private ProductLevel(String k) {
        this.key = k;
    }

    public String getKey() {
        return key;
    }

}
