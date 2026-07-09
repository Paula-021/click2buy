package com.paula.click2buy.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.paula.click2buy.domain.Cart;
import com.paula.click2buy.domain.ItemCart;
import com.paula.click2buy.domain.Product;
import com.paula.click2buy.domain.ShippingOption;
import com.paula.click2buy.endpoints.dtos.CartRequestDTO;
import com.paula.click2buy.endpoints.dtos.CartShippingCalculateRequestDTO;
import com.paula.click2buy.exceptions.CartNotFoundException;
import com.paula.click2buy.exceptions.StockQuantityNotFoundException;
import com.paula.click2buy.repositories.CartRepository;
import com.paula.click2buy.repositories.ShippingOptionRepository;
import com.paula.click2buy.shipment.dtos.MelhorEnvioProductDTO;
import com.paula.click2buy.shipment.dtos.ShipmentRequestDTO;
import com.paula.click2buy.shipment.dtos.ShipmentResponseDTO;
import com.paula.click2buy.shipment.services.MelhorEnvioShipmentCalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private MelhorEnvioShipmentCalculateService melhorEnvioShipmentCalculateService;
    @Autowired
    private ShippingOptionRepository shippingOptionRepository;

    @Override
    public Cart addCart() {
//        List<ItemCart> listItemCart = cartRequestDTO.getListItemCartDTO().stream()
//                .map(itemCartDTO -> {
//                            Product product = productService.getProductById(itemCartDTO.getProductId());
//
//                            return itemCartDTO.toEntity(product);
//                        }
//                )
//                .toList();

//        cart.setListItemCart(listItemCart);

        Cart cart = new Cart(); //null
        List<ItemCart> itemCarts = new ArrayList<>();
        cart.setListItemCart(itemCarts);
        return cartRepository.save(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartById(Long id) {
        //pegar o carrinho
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CartNotFoundException());
        //verificar se tem estoque nos itens, se nao tiver, atualizar o atributo hasStock para false
        cart.getListItemCart().forEach(itemCart -> {
            int stockQuantity = itemCart.getProduct().getStockQuantity();
            if (itemCart.getQuantity() > stockQuantity) {
                itemCart.setHasStock(false);
            } else {
                itemCart.setHasStock(true);
            }
        });

        Double totalPrice = calculateTotalPrice(cart);
        cart.setTotalPriceBrl(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts;
    }

    @Override
    public Cart addItemsToCart(Long cartId, CartRequestDTO cartRequestDTO) {

        //convertar ItemCartRequestDTO para ItemCart
        List<ItemCart> listItemCart = cartRequestDTO.getListItemCartDTO().stream()
                .map(itemCartDTO -> {
                            Product product = productService.getProductById(itemCartDTO.getProductId());

                            return itemCartDTO.toEntity(product);
                        }
                )
                .toList();

        //pega o carrinho
        Cart cart = getCartById(cartId);


        // GARANTE que a lista existe
        if (cart.getListItemCart() == null) {
            cart.setListItemCart(new ArrayList<>());
        }

        // Adiciona corretamente item a item
        for (ItemCart item : listItemCart) {

            // validar, antes de adicionar no carrinho, se a quantidade de estoque é suficiente

            int stockQuantity = item.getProduct().getStockQuantity();

            if (item.getQuantity() > stockQuantity) {
                throw new StockQuantityNotFoundException(" Quantity requested for product " +
                        item.getProduct().getName() + " exceeds the stock quantity.");
            }


            // a cada requisição de busca por um carrinho, verificar e atualizar o atributo hasStock de cada ItemCart



            //se já existe o produto no carrinho, só atualiza a quantidade
            boolean found = false;
            for (ItemCart existingItem : cart.getListItemCart()) {
                if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                    if(existingItem.getQuantity() + item.getQuantity() > stockQuantity){
                        throw new StockQuantityNotFoundException("Quantity requested for product " +
                                existingItem.getProduct().getName() + " exceeds the stock quantity.");
                    }

                    existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
//                    int quantity = existingItem.getQuantity();
//                    Product product = existingItem.getProduct();
//                    product.setStockQuantity(product.getStockQuantity() - quantity);
                    found = true;
                    break;
                }
            }

            if(!found){
                item.setCart(cart); // MUITO IMPORTANTE
                cart.getListItemCart().add(item);
            }
        }
        return cartRepository.save(cart);
    }
    public Cart updateQuantityOneItemFromCart(Long cartId, Long itemId, String action){
        if(action.equals("add")){
            Cart cart = addOneUnityItemFromCart(cartId, itemId);
            return cart;//200
        }else if(!action.equals("remove")){
            Cart cart = removeOneUnityItemFromCart(cartId, itemId);
            return cart;
        }
        return null;
    }

    @Override
    public Cart removeOneUnityItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException());
        List<ItemCart> itemCartList = cart.getListItemCart();
        for (ItemCart itemCart : itemCartList) {
            if(itemCart.getId().equals(itemId)){

                itemCart.setQuantity(itemCart.getQuantity() - 1);
                if(itemCart.getQuantity() <= 0){
                    itemCartList.remove(itemCart);
                }

                break;
            }
        }
        cart.setListItemCart(itemCartList);
        return cartRepository.save(cart);
    }
    @Override
    public Cart addOneUnityItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException());
        List<ItemCart> itemCartList = cart.getListItemCart();
        for (ItemCart itemCart : itemCartList) {
            if(itemCart.getId().equals(itemId)){
                if(itemCart.getQuantity() + 1 > itemCart.getProduct().getStockQuantity()){
                    throw new StockQuantityNotFoundException(" Quantity requested for product " +
                            itemCart.getProduct().getName() + " exceeds the stock quantity.");
                }

                itemCart.setQuantity(itemCart.getQuantity() + 1);
                break;
            }
        }
        cart.setListItemCart(itemCartList);
        return cartRepository.save(cart);
    }


    @Override
    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException());
        cart.getListItemCart().removeIf(item -> item.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public List<ShipmentResponseDTO> calculateShipping(Cart cart, CartShippingCalculateRequestDTO cartShippingCalculateRequestDTO) throws JsonProcessingException {

        //do cart o que nos interessa é os produtos
        List<MelhorEnvioProductDTO> melhorEnvioProductDTOList = new ArrayList<>();

        List<ItemCart> listItemCart = cart.getListItemCart();
        listItemCart.forEach(itemCart -> {
            Product product = itemCart.getProduct();
            int quantity = itemCart.getQuantity();

            MelhorEnvioProductDTO melhorEnvioProductDTO = new MelhorEnvioProductDTO();
            melhorEnvioProductDTO.setProduct(product);
            melhorEnvioProductDTO.setQuantity(quantity);

            melhorEnvioProductDTOList.add(melhorEnvioProductDTO);

        });

        String sender = cartShippingCalculateRequestDTO.getZipCodeSender();
        String recipient = cartShippingCalculateRequestDTO.getZipCodeRecipient();

        ShipmentRequestDTO shipmentRequestDTO = new ShipmentRequestDTO();
        shipmentRequestDTO.setSender(sender);
        shipmentRequestDTO.setRecipient(recipient);
        shipmentRequestDTO.setProducts(melhorEnvioProductDTOList);

         List<ShipmentResponseDTO> shipmentResponseDTOList = melhorEnvioShipmentCalculateService.calculateShipment(shipmentRequestDTO);

         return shipmentResponseDTOList;

        //cartShippingCalculateRequestDTO -> cep de origem e cep de destino


    }

    @Override
    public Double calculateTotalPrice(Cart cart) {
        Double totalPrice = cart.getListItemCart().stream()
                .mapToDouble(itemCart -> {
                    if(!itemCart.isHasStock()) {
                        return 0.0;
                    }
                    return itemCart.getProduct().getPrice() * itemCart.getQuantity();
                })
                .sum();

        if (cart.getShippingSelected() != null) {
            totalPrice += cart.getShippingSelected().getPrice();
        }



        return totalPrice;
    }

    public ShippingOption addShippingOption(ShippingOption shippingOption) {
        return shippingOptionRepository.save(shippingOption);
    }


}
