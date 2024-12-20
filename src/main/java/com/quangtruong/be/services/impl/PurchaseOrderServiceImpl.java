package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.PurchaseOrderDTO;
import com.quangtruong.be.dto.PurchaseOrderDetailDTO;
import com.quangtruong.be.entities.PurchaseOrder;
import com.quangtruong.be.entities.PurchaseOrderDetail;
import com.quangtruong.be.repositories.PurchaseOrderRepository;
import com.quangtruong.be.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    public PurchaseOrderDTO convertToDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
        dto.setSupplierId(purchaseOrder.getSupplier().getSupplierId());
        dto.setSupplierName(purchaseOrder.getSupplier().getSupplierName());
        dto.setOrderDate(purchaseOrder.getOrderDate());
        dto.setTotalAmount(purchaseOrder.getTotalAmount());
        dto.setPurchaseOrderDetails(purchaseOrder.getPurchaseOrderDetails().stream()
                .map(this::convertPurchaseOrderDetailToDto)
                .collect(Collectors.toList()));
        return dto;
    }
    public List<PurchaseOrderDTO> toDtoList(List<PurchaseOrder> purchaseOrders){
        return purchaseOrders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PurchaseOrderDetailDTO convertPurchaseOrderDetailToDto(PurchaseOrderDetail purchaseOrderDetail) {
        PurchaseOrderDetailDTO dto = new PurchaseOrderDetailDTO();
        dto.setPurchaseOrderDetailId(purchaseOrderDetail.getPurchaseOrderDetailId());
        dto.setProductId(purchaseOrderDetail.getProduct().getProductID());
        dto.setProductName(purchaseOrderDetail.getProduct().getProductName());
        dto.setQuantity(purchaseOrderDetail.getQuantity());
        dto.setUnitCost(purchaseOrderDetail.getUnitCost());
        dto.setTotalCost(purchaseOrderDetail.getTotalCost());
        return dto;
    }
}