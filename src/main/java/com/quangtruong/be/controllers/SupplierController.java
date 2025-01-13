package com.quangtruong.be.controllers;

import com.quangtruong.be.dto.SupplierDTO;
import com.quangtruong.be.entities.Supplier;
import com.quangtruong.be.services.SupplierService;
import com.quangtruong.be.services.impl.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierServiceImpl supplierServiceImpl; // Inject SupplierServiceImpl để sử dụng các phương thức chuyển đổi

    // Trả về List<SupplierDTO> thay vì List<Supplier>
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        List<SupplierDTO> supplierDTOs = supplierServiceImpl.toDtoList(suppliers);
        return new ResponseEntity<>(supplierDTOs, HttpStatus.OK);
    }

    // Trả về SupplierDTO thay vì Supplier
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) throws Exception {
        Supplier supplier = supplierService.findById(id);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(supplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }

    // Trả về SupplierDTO sau khi tạo thay vì Supplier
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody Supplier supplier) throws Exception {
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(savedSupplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.CREATED);
    }

    // Trả về SupplierDTO sau khi cập nhật thay vì Supplier
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) throws Exception {
        Supplier updatedSupplier = supplierService.updateSupplier(id, supplier);
        SupplierDTO supplierDTO = supplierServiceImpl.convertToDto(updatedSupplier);
        return new ResponseEntity<>(supplierDTO, HttpStatus.OK);
    }

    // DELETE vẫn trả về Void
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) throws Exception {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


