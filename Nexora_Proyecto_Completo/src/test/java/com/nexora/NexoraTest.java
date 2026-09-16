package com.nexora;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import com.nexora.domain.*;
import com.nexora.enums.*;
import com.nexora.service.*;

public class NexoraTest {
    @Test
    void noPermiteCorreoDuplicado() {
        UserService service = new UserService();
        service.registerBuyer(1L, "Ana", "ana@nexora.com");
        assertThrows(IllegalArgumentException.class,
                () -> service.registerBuyer(2L, "Luis", "ana@nexora.com"));
    }

    @Test
    void soloAdministradorPuedeIncorporarVendedor() {
        UserService service = new UserService();
        User buyer = service.registerBuyer(1L, "Ana", "ana@nexora.com");
        assertThrows(IllegalArgumentException.class,
                () -> service.onboardSeller(buyer, 2L, "Luis", "luis@nexora.com"));
    }

    @Test
    void noPermiteReservarMasDeLoDisponible() {
        InventoryService service = new InventoryService();
        Inventory inventory = service.createInventory(1L, 2L, 3L, 5);
        assertThrows(IllegalArgumentException.class, () -> service.reserve(inventory.getId(), 6, false));
    }

    @Test
    void noPermiteReservarInventarioDanado() {
        InventoryService service = new InventoryService();
        Inventory inventory = service.createInventory(1L, 2L, 3L, 5);
        assertThrows(IllegalArgumentException.class, () -> service.reserve(inventory.getId(), 1, true));
    }

    @Test
    void pedidoSigueElCicloCompleto() {
        UserService users = new UserService();
        User admin = users.register(1L, "Admin", "admin@nexora.com", Role.ADMINISTRATOR);
        User buyer = users.registerBuyer(2L, "Buyer", "buyer@nexora.com");
        User seller = users.onboardSeller(admin, 3L, "Seller", "seller@nexora.com");
        ProductService products = new ProductService();
        Product product = products.createProduct(seller, 10L, "Producto", "Demo", new BigDecimal("100"), ProductType.PHYSICAL, 1L);
        CartService carts = new CartService();
        Cart cart = carts.createCart(20L, buyer.getId());
        carts.addItem(cart, 21L, product.getId(), 1);
        OrderService orders = new OrderService();
        Order order = orders.createOrder(30L, buyer.getId(), cart, products.getRepository());
        orders.pay(order); orders.dispatch(order); orders.deliver(order); orders.finalizeOrder(order);
        assertEquals(OrderStatus.FINALIZED, order.getStatus());
    }

    @Test
    void pedidoFinalizadoNoSePuedeModificar() {
        Order order = new Order(1L, 2L, OrderStatus.FINALIZED, java.time.LocalDateTime.now(), BigDecimal.TEN);
        assertThrows(IllegalStateException.class, () -> order.setTotal(BigDecimal.ONE));
    }
}
