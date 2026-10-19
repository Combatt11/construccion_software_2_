package com.nexora;

import java.math.BigDecimal;
import com.nexora.adapters.out.persistence.InMemoryAuditAdapter;
import com.nexora.adapters.out.persistence.InMemoryInventoryMovementRepository;
import com.nexora.adapters.out.persistence.InMemoryInventoryRepository;
import com.nexora.adapters.out.persistence.InMemoryOrderRepository;
import com.nexora.adapters.out.persistence.InMemoryProductRepository;
import com.nexora.adapters.out.persistence.InMemoryUserRepository;
import com.nexora.application.services.CartService;
import com.nexora.application.services.InventoryService;
import com.nexora.application.services.OrderService;
import com.nexora.application.services.ProductService;
import com.nexora.application.services.UserService;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.Inventory;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;

public class Main {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();
        InMemoryInventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        InMemoryInventoryMovementRepository movementRepository = new InMemoryInventoryMovementRepository();

        UserService userService = new UserService(userRepository, new InMemoryAuditAdapter());
        ProductService productService = new ProductService(productRepository);
        InventoryService inventoryService = new InventoryService(inventoryRepository, movementRepository);
        CartService cartService = new CartService();
        OrderService orderService = new OrderService(orderRepository, productRepository);

        User administrator = userService.register(1L, "Administrador", "admin@nexora.com", Role.ADMINISTRATOR);
        User buyer = userService.registerBuyer(2L, "Comprador", "buyer@nexora.com");
        User seller = userService.onboardSeller(administrator, 3L, "Vendedor", "seller@nexora.com");

        Product product = productService.createProduct(seller, 100L, "Audífonos Nexora",
                "Producto de prueba", new BigDecimal("120000"), ProductType.PHYSICAL, 10L);

        Inventory inventory = inventoryService.createInventory(200L, product.getId(), 300L, 10);
        inventoryService.reserve(inventory.getId(), 2, false);

        Cart cart = cartService.createCart(400L, buyer.getId());
        cartService.addItem(cart, 401L, product.getId(), 2);

        Order order = orderService.createOrder(500L, buyer.getId(), cart);
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
