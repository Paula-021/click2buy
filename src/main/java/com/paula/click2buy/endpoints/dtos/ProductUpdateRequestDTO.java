package com.paula.click2buy.endpoints.dtos;
public class ProductUpdateRequestDTO {

    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;

    public String getName() {
        if(name == null){
            name = "";
        }else{
            String logradouroTrim = name.trim();
            if(logradouroTrim.isEmpty()){
                return "";
            }else{
                return name; //seria realmente o nome de uma rua
            }
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if(description == null){
            description = "";
        }else{
            String logradouroTrim = description.trim();
            if(logradouroTrim.isEmpty()){
                return "";
            }else{
                return description; //seria realmente o nome de uma rua
            }
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        if(price == null){
            price = 0.0;
        }
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        if(stockQuantity == null){
            stockQuantity = 0;
        }
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
