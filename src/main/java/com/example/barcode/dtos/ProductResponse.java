package com.example.barcode.dtos;

public record ProductResponse(String name, String barcode, byte[] barcodeImage) {
}
