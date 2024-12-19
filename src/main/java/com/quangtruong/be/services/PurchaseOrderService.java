package com.quangtruong.be.services;

import com.quangtruong.be.entities.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllPurchaseOrders();
    Optional<PurchaseOrder> getPurchaseOrderById(Long id);
    PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);
    void deletePurchaseOrder(Long id);
}