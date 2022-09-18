package com.geekbrains.spring.web.dto;

import com.geekbrains.spring.web.entities.Product;
import lombok.Data;
import org.springframework.cache.CacheManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Data
public class  Cart {

    private List<OrderItemDto> items;
    private  int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String cartName, CacheManager manager){
        Cart cart = manager.getCache("Cart").get(cartName, Cart.class);
        if(Optional.ofNullable(cart).isPresent()){
            this.items = cart.getItems();
            this.totalPrice = cart.getTotalPrice();
        } else {
            this.items = new ArrayList<>();
            this.totalPrice = 0;
            manager.getCache("Cart").put(cartName, Cart.class);
        }
    }

    /*
    Данный метод вызывается методом addProduct() и просто проверяет есть ли продукт в корзине, то есть добавлялся ли он ранее.
    Если есть, то идем в метод changeQuantity и меняем количество существующего товара.
    */
    public boolean addProductCount(Long id){
        for(OrderItemDto o: items){
            if(o.getProductId().equals(id)){
                o.changeQuantity(1);
                recalculate(); // увеличения количества пересчитываем стоимость всей корзины.
                return true;
            }
        }
        return false;
    }
/*
Каждый раз нажимая на фронте кнопку "добавить продукт" имеем два сценария:
Первый продукт был ранее добавлен и мы увеличиваем его количество в той же сущ. строке на фронте.
Второй продукт не был ранее добавлен на фронт и мы его далее добавляем.
*/
    public void addProduct(Product product){
        // Если продукт был добавлен, то после проверки ниже мы выйдем из метода.
        if(addProductCount(product.getId())){
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }
    // Метод для пересчета общей цены в корзине.
    private void recalculate(){
        totalPrice = 0;
        for(OrderItemDto o: items){
            totalPrice += o.getPrice();
        }
    }

    public void removeProduct(Long id){
        items.removeIf(o -> o.getProductId().equals(id));
        recalculate();
    }

    public void decreaseProduct(Long id){
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()){
            OrderItemDto o = iter.next();
            if(o.getProductId().equals(id)){
                o.changeQuantity(-1);
                if(o.getQuantity() <= 0){
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void clear(){
        items.clear();
        totalPrice = 0;
    }
}
