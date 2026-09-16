package com.nexora;

import java.math.BigDecimal;
import com.nexora.domain.Cart;
import com.nexora.domain.Inventory;
import com.nexora.domain.Order;
import com.nexora.domain.Product;
import com.nexora.domain.User;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;
import com.nexora.service.CartService;
import com.nexora.service.InventoryService;
import com.nexora.service.OrderService;
import com.nexora.service.ProductService;
import com.nexora.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        ProductService productService = new ProductService();
        InventoryService inventoryService = new InventoryService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        User administrator = userService.register(1L, "Administrador", "admin@nexora.com", Role.ADMINISTRATOR);
        User buyer = userService.registerBuyer(2L, "Comprador", "buyer@nexora.com");
        User seller = userService.onboardSeller(administrator, 3L, "Vendedor", "seller@nexora.com");

        Product product = productService.createProduct(seller, 100L, "Audífonos Nexora",
                "Producto de prueba", new BigDecimal("120000"), ProductType.PHYSICAL, 10L);

        Inventory inventory = inventoryService.createInventory(200L, product.getId(), 300L, 10);
        inventoryService.reserve(inventory.getId(), 2, false);

        Cart cart = cartService.createCart(400L, buyer.getId());
        cartService.addItem(cart, 401L, product.getId(), 2);

        Order order = orderService.createOrder(500L, buyer.getId(), cart, productService.getRepository());
        orderService.pay(order);
        orderService.dispatch(order);
        orderService.deliver(order);
        orderService.finalizeOrder(order);

        System.out.println("=== NEXORA ===");
        System.out.println("Vendedor incorporado: " + seller.getName());
        System.out.println("Producto: " + product.getName());
        System.out.println("Inventario disponible: " + inventory.getAvailableQuantity());
        System.out.println("Pedido: " + order.getStatus());
        System.out.println("Total: $" + order.getTotal());
    }
}
