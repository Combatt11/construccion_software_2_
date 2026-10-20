# Data Model — Nexora

## Relacional (MySQL)

Tablas principales:
- `users` (id, name, email, role, status, password_hash, auth_token_version)
- `products` (id, name, description, price, type, status, seller_id, category_id)
- `orders` (id, buyer_id, status, creation_date, total)
- `order_items` (id, order_id, product_id, quantity, unit_price, subtotal)
- `inventories` (id, product_id, warehouse_id, available_quantity, reserved_quantity)
- `inventory_movements` (id, inventory_id, type, quantity, date)

Otras entidades del dominio: Category, Warehouse, Cart, Shipment, Invoice, Return, Refund.

## NoSQL (MongoDB)

Colección `audit_logs`:
- id, timestamp, requestId, userId, action, entityType, entityId, payload (Map), result.
