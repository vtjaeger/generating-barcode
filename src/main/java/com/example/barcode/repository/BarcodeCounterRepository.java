package com.example.barcode.repository;

import com.example.barcode.model.BarcodeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeCounterRepository extends JpaRepository<BarcodeCounter, Long> {
}
