package com.quangtruong.be.services;

import com.quangtruong.be.entities.PurchaseOrderDetail;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderDetailService {
    List<PurchaseOrderDetail> getAllPurchaseOrderDetails();
    Optional<PurchaseOrderDetail> getPurchaseOrderDetailById(Long id);
    PurchaseOrderDetail savePurchaseOrderDetail(PurchaseOrderDetail purchaseOrderDetail);
    void deletePurchaseOrderDetail(Long id);
}