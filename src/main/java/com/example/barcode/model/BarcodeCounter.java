package com.example.barcode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "barcode_counter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeCounter {
    @Id
    private Long id;
    private int nextBarcodeNumber;
}
