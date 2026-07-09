package com.paula.click2buy.domain;

import jakarta.persistence.*;

@Entity
public class ItemCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Integer quantity;

    private boolean hasStock;
    // 1. usuario foi adicionar um item no carrinho: verificar se tem estoque antes de adicionar
    // 2. usuario foi visualizar o carrinho: verificar se tem estoque nos itens adicionados (se não tiver, atualizar o atributo hasStock para false e informar o usuário que tem itens sem estoque)
    // 3. usuario foi atualizar a quantidade de um item no carrinho: verificar se tem estoque antes de atualizar a quantidade
    // 4. usuario foi finalizar a compra: verificar se tem estoque antes de finalizar a compra (se não tiver, informar o usuário que tem itens sem estoque e não finalizar a compra)

    //testar as 4 possibilidades acima para garantir que o sistema está verificando o estoque corretamente e informando o usuário quando necessário.

    private boolean isSelected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isHasStock() {
        return hasStock;
    }
    public void setHasStock(boolean hasStock) {
        this.hasStock = hasStock;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
