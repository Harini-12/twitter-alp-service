package com.alp.dto;

import java.util.List;

public class ProductCountResponse {

    List<ProductCountDTO> productCountList;

    public List<ProductCountDTO> getProductCountList() {
        return productCountList;
    }

    public void setProductCountList(List<ProductCountDTO> productCountList) {
        this.productCountList = productCountList;
    }

}
