# Business Rules — Nexora

## Users

1. The user identifier must be unique.
2. The email address must be unique.
3. Each user has a single role.
4. Operations require an authenticated user.
5. Permissions depend on the role.

## Sellers

1. A seller cannot register directly.
2. The seller must be onboarded by an administrator.

## Inventory

1. Each inventory record is related to one product and one warehouse.
2. Negative stock is not allowed.
3. A nonexistent quantity cannot be reserved.
4. Inventory marked as damaged cannot be reserved.

## Orders

1. The order follows a lifecycle of states.
2. A finalized order cannot be modified.

## Roles

- BUYER
- SELLER
- ADMINISTRATOR
- LOGISTICS_OPERATOR
- SUPERVISOR

The rules above are based on the functional specification delivered for the project.
