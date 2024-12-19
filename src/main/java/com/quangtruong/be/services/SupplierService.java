package com.quangtruong.be.services;

import com.quangtruong.be.entities.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(Long id);
    Supplier saveSupplier(Supplier supplier);
    void deleteSupplier(Long id);
}