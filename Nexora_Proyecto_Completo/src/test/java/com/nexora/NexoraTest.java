package com.nexora;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import com.nexora.adapters.out.persistence.InMemoryInventoryMovementRepository;
import com.nexora.adapters.out.persistence.InMemoryInventoryRepository;
import com.nexora.adapters.out.persistence.InMemoryOrderRepository;
import com.nexora.adapters.out.persistence.InMemoryProductRepository;
import com.nexora.adapters.out.persistence.InMemoryUserRepository;
import com.nexora.application.services.InventoryService;
import com.nexora.application.services.OrderService;
import com.nexora.application.services.ProductService;
import com.nexora.application.services.UserService;
import com.nexora.domain.models.*;
import com.nexora.enums.*;

public class NexoraTest {
    @Test
    void noPermiteCorreoDuplicado() {
        UserService service = new UserService(new InMemoryUserRepository());
        service.registerBuyer(1L, "Ana", "ana@nexora.com");
        assertThrows(IllegalArgumentException.class,
                () -> service.registerBuyer(2L, "Luis", "ana@nexora.com"));
    }

    @Test
    void soloAdministradorPuedeIncorporarVendedor() {
        UserService service = new UserService(new InMemoryUserRepository());
        User buyer = service.registerBuyer(1L, "Ana", "ana@nexora.com");
        assertThrows(IllegalArgumentException.class,
                () -> service.onboardSeller(buyer, 2L, "Luis", "luis@nexora.com"));
    }

    @Test
    void noPermiteReservarMasDeLoDisponible() {
        InventoryService service = new InventoryService(new InMemoryInventoryRepository(), new InMemoryInventoryMovementRepository());
        Inventory inventory = service.createInventory(1L, 2L, 3L, 5);
        assertThrows(com.nexora.domain.exceptions.InsufficientStockException.class, () -> service.reserve(inventory.getId(), 6, false));
    }

    @Test
    void noPermiteReservarInventarioDanado() {
        InventoryService service = new InventoryService(new InMemoryInventoryRepository(), new InMemoryInventoryMovementRepository());
        Inventory inventory = service.createInventory(1L, 2L, 3L, 5);
        assertThrows(IllegalArgumentException.class, () -> service.reserve(inventory.getId(), 1, true));
    }

    @Test
    void pedidoSigueElCicloCompleto() {
        InMemoryUserRepository usersRepo = new InMemoryUserRepository();
        InMemoryProductRepository productsRepo = new InMemoryProductRepository();
        UserService users = new UserService(usersRepo);
        User admin = users.register(1L, "Admin", "admin@nexora.com", Role.ADMINISTRATOR);
        User buyer = users.registerBuyer(2L, "Buyer", "buyer@nexora.com");
        User seller = users.onboardSeller(admin, 3L, "Seller", "seller@nexora.com");
        ProductService products = new ProductService(productsRepo);
        Product product = products.createProduct(seller, 10L, "Producto", "Demo", new BigDecimal("100"), ProductType.PHYSICAL, 1L);
        com.nexora.application.services.CartService carts = new com.nexora.application.services.CartService();
        Cart cart = carts.createCart(20L, buyer.getId());
        carts.addItem(cart, 21L, product.getId(), 1);
        InMemoryOrderRepository ordersRepo = new InMemoryOrderRepository();
        OrderService orders = new OrderService(ordersRepo, productsRepo);
        Order order = orders.createOrder(30L, buyer.getId(), cart);
        orders.pay(order); orders.dispatch(order); orders.deliver(order); orders.finalizeOrder(order);
        assertEquals(OrderStatus.FINALIZED, order.getStatus());
    }

    @Test
    void pedidoFinalizadoNoSePuedeModificar() {
        Order order = new Order(1L, 2L, OrderStatus.FINALIZED, java.time.LocalDateTime.now(), BigDecimal.TEN);
        assertThrows(IllegalStateException.class, () -> order.setTotal(BigDecimal.ONE));
    }
}
