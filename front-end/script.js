document.addEventListener('DOMContentLoaded', () => {
    const BASE_URL = 'http://localhost:8080/product';
    const productTable = document.getElementById('productTable').getElementsByTagName('tbody')[0];

    async function loadProducts() {
        try {
            const response = await fetch(BASE_URL);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const products = await response.json();

            productTable.innerHTML = '';

            products.forEach(product => {
                const row = productTable.insertRow();
                row.insertCell(0).textContent = product.id;
                row.insertCell(1).textContent = product.name;

                const barcodeTextCell = row.insertCell(2);
                barcodeTextCell.textContent = product.barcode || 'N/A';

                const barcodeImageCell = row.insertCell(3);
                if (product.barcode) {
                    const barcodeImage = document.createElement('img');
                    barcodeImage.src = `http://localhost:8080/barcode/${product.barcode}`; 
                    barcodeImage.alt = 'Código de Barras';
                    barcodeImage.style.width = '100px'; 
                    barcodeImageCell.appendChild(barcodeImage);
                } else {
                    barcodeImageCell.textContent = 'N/A';
                }
            });
        } catch (error) {
            console.error('Error loading products:', error);
            alert('Error loading products');
        }
    }

    loadProducts();
});
