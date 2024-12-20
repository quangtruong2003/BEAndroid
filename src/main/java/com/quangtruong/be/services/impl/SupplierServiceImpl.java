package com.quangtruong.be.services.impl;

import com.quangtruong.be.dto.SupplierDTO;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.repositories.SupplierRepository;
import com.quangtruong.be.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier findById(Long id) throws Exception {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new Exception("Supplier not found with id: " + id));
    }
    @Override
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) throws Exception {
        Supplier supplier = findById(id);
        supplier.setSupplierName(updatedSupplier.getSupplierName());
        supplier.setContactName(updatedSupplier.getContactName());
        supplier.setAddress(updatedSupplier.getAddress());
        supplier.setPhone(updatedSupplier.getPhone());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setWebsite(updatedSupplier.getWebsite());
        supplier.setUpdatedAt(LocalDateTime.now());
        return supplierRepository.save(supplier);
    }
    @Override
    public void deleteSupplier(Long id) throws Exception {
        Supplier supplier = findById(id);
        supplierRepository.delete(supplier);
    }

    public SupplierDTO convertToDto(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierId(supplier.getSupplierId());
        dto.setSupplierName(supplier.getSupplierName());
        dto.setContactName(supplier.getContactName());
        dto.setAddress(supplier.getAddress());
        dto.setPhone(supplier.getPhone());
        dto.setEmail(supplier.getEmail());
        dto.setWebsite(supplier.getWebsite());
        return dto;
    }

    public List<SupplierDTO> toDtoList(List<Supplier> suppliers){
        return suppliers.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}