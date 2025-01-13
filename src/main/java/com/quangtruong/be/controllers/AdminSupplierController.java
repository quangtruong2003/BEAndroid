package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.SupplierDTO;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.services.SupplierService;
import com.quangtruong.be.services.impl.SupplierServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/suppliers")
public class AdminSupplierController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierServiceImpl supplierServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody Supplier supplier) {
        Supplier createdSupplier = supplierService.saveSupplier(supplier);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(createdSupplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @Valid @RequestBody Supplier supplier) throws Exception {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(updatedSupplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) throws Exception {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        List<SupplierDTO> supplierDTOs = supplierServiceImpl.toDtoList(suppliers);
        return new ResponseEntity<>(supplierDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) throws Exception {
        Supplier supplier = supplierService.findById(id);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(supplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }
}
