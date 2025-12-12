--CREATE DATABASE smart_inventory_db;
--USE smart_inventory_db;

-- 1. Tabla de Usuarios (Espejo de Firebase)
-- El 'firebase_uid' es el link clave con la autenticación de Google/Firebase
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firebase_uid VARCHAR(128) NOT NULL UNIQUE, -- ID único que viene de Firebase Auth
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    role ENUM('ADMIN', 'EMPLOYEE') DEFAULT 'EMPLOYEE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabla de Categorías
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN DEFAULT TRUE
);

-- 3. Tabla de Productos (El corazón del sistema)
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    barcode VARCHAR(100) , -- Crucial para el escáner
    name VARCHAR(150) NOT NULL,
    description TEXT,
    image_url VARCHAR(500), -- Aquí guardamos la URL que nos da Firebase Storage
    
    -- Precios y Stock
    price_cost DECIMAL(10, 2) NOT NULL, -- Cuánto me costó
    price_sale DECIMAL(10, 2) NOT NULL, -- A cuánto lo vendo
    stock_current INT NOT NULL DEFAULT 0,
    stock_min INT DEFAULT 5, -- Para alertas de "Stock Bajo"
    
    -- Fechas importantes
    expiration_date DATE, -- Para alertas de "Por vencer"
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Vital para sincronización
    
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 4. Tabla de Movimientos (Historial/Kardex)
-- Registra quién modificó el stock y por qué
CREATE TABLE movements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL, -- Quién hizo el movimiento
    
    type ENUM('IN', 'OUT', 'ADJUSTMENT') NOT NULL, -- Entrada, Salida (Venta/Merma), Ajuste
    quantity INT NOT NULL,
    notes VARCHAR(255), -- Ej: "Merma por rotura", "Compra factura #123"
    movement_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Insertar datos de prueba (Seed Data)
INSERT INTO categories (name, description) VALUES 
('Bebidas', 'Gaseosas, aguas y jugos'),
('Snacks', 'Galletas, papitas y dulces');
