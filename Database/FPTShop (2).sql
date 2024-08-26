CREATE DATABASE FPTShop;
USE FPTShop;
GO

USE master;
GO

-- Ngắt tất cả các kết nối đến cơ sở dữ liệu
ALTER DATABASE FPTShop
SET SINGLE_USER
WITH ROLLBACK IMMEDIATE;
GO

-- Xóa cơ sở dữ liệu
DROP DATABASE FPTShop;
GO

-- Bảng Thương Hiệu
CREATE TABLE Brands (
    brandID NVARCHAR(50) PRIMARY KEY,
    brandName NVARCHAR(50) NOT NULL
);

-- Bảng Vai Trò
CREATE TABLE Roles (
    roleID NVARCHAR(50) PRIMARY KEY,
    roleName NVARCHAR(50) NOT NULL
);

-- Bảng Người Dùng
CREATE TABLE Users (
    userID NVARCHAR(50) PRIMARY KEY,
    fullName NVARCHAR(50) NOT NULL,
    password NVARCHAR(50) NOT NULL,
    roleID NVARCHAR(50) NOT NULL,
    status BIT NOT NULL,
    FOREIGN KEY (roleID) REFERENCES Roles(roleID)
);

-- Bảng Danh Mục Sản Phẩm
CREATE TABLE Categories (
    categoryID NVARCHAR(50) PRIMARY KEY,
    categoryName NVARCHAR(50) NOT NULL
);

-- Bảng Sản Phẩm
CREATE TABLE Products (
    productID NVARCHAR(50) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    quantity INT NOT NULL,
    categoryID NVARCHAR(50),
    brandID NVARCHAR(50),
    imageURL NVARCHAR(255),
    FOREIGN KEY (categoryID) REFERENCES Categories(categoryID),
    FOREIGN KEY (brandID) REFERENCES Brands(brandID)
);

-- Bảng Chi Tiết Sản Phẩm
CREATE TABLE ProductDetails (
    productDetailID NVARCHAR(50) PRIMARY KEY,
    productID NVARCHAR(50) NOT NULL,
    description NVARCHAR(MAX) NOT NULL,
    specifications NVARCHAR(MAX) NOT NULL,
    warrantyPeriod NVARCHAR(50) NOT NULL,
    FOREIGN KEY (productID) REFERENCES Products(productID)
);


-- Bảng Đơn Hàng
CREATE TABLE Orders (
    orderID NVARCHAR(50) PRIMARY KEY,
    userID NVARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    total DECIMAL(18, 2) NOT NULL,
    status BIT NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users(userID)
);

-- Bảng Chi Tiết Đơn Hàng
CREATE TABLE OrderDetails (
    orderDetailID NVARCHAR(50) PRIMARY KEY,
    orderID NVARCHAR(50) NOT NULL,
    productID NVARCHAR(50) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    quantity INT NOT NULL,
    status BIT NOT NULL,
    FOREIGN KEY (orderID) REFERENCES Orders(orderID),
    FOREIGN KEY (productID) REFERENCES Products(productID)
);

-- Bảng Giỏ Hàng
CREATE TABLE Cart (
    cartID NVARCHAR(50) PRIMARY KEY,
    userID NVARCHAR(50) NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users(userID)
);

-- Bảng Chi Tiết Giỏ Hàng
CREATE TABLE CartItem (
    cartItemID NVARCHAR(50) PRIMARY KEY,
    cartID NVARCHAR(50) NOT NULL,
    productID NVARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (cartID) REFERENCES Cart(cartID),
    FOREIGN KEY (productID) REFERENCES Products(productID)
);

-- Thêm Thương Hiệu
INSERT INTO Brands (brandID, brandName) VALUES 
('B1', 'Apple'),
('B2', 'Samsung'),
('B3', 'Sony'),
('B4', 'Dell'),
('B5', 'HP'),
('B6', 'Lenovo'),
('B7', 'Asus'),
('B8', 'Acer'),
('B9', 'MSI'),
('B10', 'Razer'),
('B11', 'Intel'),
('B12', 'AMD'),
('B13', 'Corsair'),
('B14', 'Seagate'),
('B15', 'Western Digital'),
('B16', 'Logitech'),
('B17', 'JBL'),
('B18', 'Sony'),
('B19', 'Bose'),
('B20', 'Anker');

-- Thêm Danh Mục
INSERT INTO Categories (categoryID, categoryName) VALUES 
('C1', N'Điện Thoại'),
('C2', N'Máy Tính'),
('C3', N'Thiết Bị Điện Tử');

-- Thêm Sản Phẩm Điện Thoại
INSERT INTO Products (productID, name, price, quantity, categoryID, brandID, imageURL) 
VALUES 
('P1', N'iPhone 14', 29990000.00, 100, 'C1', 'B1', 'images/iPhone8.jpg'),
('P2', N'Samsung Galaxy S22', 25990000.00, 150, 'C1', 'B2','images/SamsungGalaxyS22.jpg'),
('P3', N'Sony Xperia 5', 19990000.00, 80, 'C1', 'B3','images/SonyXperia5.jpg'),
('P4', N'iPhone 13', 25990000.00, 120, 'C1', 'B1','images/iPhone13.jpg'),
('P5', N'Samsung Galaxy Note 20', 22990000.00, 60, 'C1', 'B2','images/SamsungGalaxyNote20.jpg'),
('P6', N'Sony Xperia 10', 15990000.00, 70, 'C1', 'B3','images/SonyXperia10.jpg'),
('P7', N'iPhone 12', 23990000.00, 90, 'C1', 'B1','images/iPhone12.jpg'),
('P8', N'Samsung Galaxy A52', 15990000.00, 140, 'C1', 'B2','images/SamsungGalaxyA52.jpg'),
('P9', N'Sony Xperia 1', 21990000.00, 50, 'C1', 'B3','images/SonyXperia1.jpg'),
('P10', N'iPhone SE', 11990000.00, 200, 'C1', 'B1','images/iPhoneSE.jpg'),
('P11', N'Samsung Galaxy M32', 13990000.00, 180, 'C1', 'B2','images/SamsungGalaxyM32.jpg'),
('P12', N'Sony Xperia 8', 12990000.00, 120, 'C1', 'B3','images/SonyXperia8.jpg'),
('P13', N'iPhone 11', 21990000.00, 80, 'C1', 'B1','images/iPhone11.jpg'),
('P14', N'Samsung Galaxy S21', 23990000.00, 110, 'C1', 'B2','images/SamsungGalaxyS21.jpg'),
('P15', N'Sony Xperia L4', 10990000.00, 150, 'C1', 'B3','images/SonyXperiaL4.jpg'),
('P16', N'iPhone XR', 18990000.00, 100, 'C1', 'B1','images/iPhoneXR.jpg'),
('P17', N'Samsung Galaxy A72', 18990000.00, 70, 'C1', 'B2','images/SamsungGalaxyA72.jpg'),
('P18', N'Sony Xperia 10 II', 14990000.00, 60, 'C1', 'B3','images/SonyXperia10II.jpg'),
('P19', N'iPhone 8', 9999000.00, 130, 'C1', 'B1','images/iPhone8.jpg'),
('P20', N'Samsung Galaxy A32', 12990000.00, 160, 'C1', 'B2','images/SamsungGalaxyA32.jpg');

-- Thêm Sản Phẩm Máy Tính
INSERT INTO Products (productID, name, price, quantity, categoryID, brandID, imageURL) 
VALUES 
('P21', N'Dell XPS 13', 24990000.00, 70, 'C2', 'B4','images/P21DellXPS13.jpg'),
('P22', N'HP Spectre x360', 22990000.00, 60, 'C2', 'B5','images/P22HPSpectrex360.jpg'),
('P23', N'Lenovo ThinkPad X1', 25990000.00, 50, 'C2', 'B6','images/P23LenovoThinkPadX1.jpg'),
('P24', N'Asus ROG Zephyrus', 29990000.00, 40, 'C2', 'B7','images/P24AsusROGZephyrus.jpg'),
('P25', N'Acer Predator Helios', 27990000.00, 45, 'C2', 'B8','images/P25AcerPredatorHelios.jpg'),
('P26', N'MSI GS66 Stealth', 28990000.00, 35, 'C2', 'B9','images/P26MSIGS66Stealth.jpg'),
('P27', N'Razer Blade 15', 31990000.00, 30, 'C2', 'B10','images/P27RazerBlade15.jpg'),
('P28', N'Dell Inspiron 15', 18990000.00, 80, 'C2', 'B4','images/P28DellInspiron15.jpg'),
('P29', N'HP Envy 13', 20990000.00, 65, 'C2', 'B5','images/P29HPEnvy13.jpg'),
('P30', N'Lenovo IdeaPad 5', 17990000.00, 90, 'C2', 'B6','images/P30LenovoIdeaPad5.jpg'),
('P31', N'Asus ZenBook 14', 19990000.00, 75, 'C2', 'B7','images/P31AsusZenBook14.jpg'),
('P32', N'Acer Aspire 7', 16990000.00, 100, 'C2', 'B8','images/P32AcerAspire7.jpg'),
('P33', N'MSI GF63', 15990000.00, 110, 'C2', 'B9','images/P33MSIGF63.jpg'),
('P34', N'Razer Book 13', 23990000.00, 50, 'C2', 'B10','images/P34RazerBook13.jpg'),
('P35', N'Dell Vostro 15', 14990000.00, 120, 'C2', 'B4','images/P35DellVostro15.jpg'),
('P36', N'HP Pavilion 14', 17990000.00, 85, 'C2', 'B5','images/P36HPPavilion14.jpg'),
('P37', N'Lenovo Yoga 7i', 21990000.00, 40, 'C2', 'B6','images/P37LenovoYoga7i.jpg'),
('P38', N'Asus TUF Gaming', 23990000.00, 55, 'C2', 'B7','images/P38AsusTUFGaming.jpg'),
('P39', N'Acer Swift 3', 15990000.00, 100, 'C2', 'B8','images/P39AcerSwift3.jpg'),
('P40', N'MSI Creator 15', 26990000.00, 30, 'C2', 'B9','images/P40MSICreator15.jpg');

-- Thêm Sản Phẩm Thiết Bị Điện Tử
INSERT INTO Products (productID, name, price, quantity, categoryID, brandID, imageURL) 
VALUES 
('P41', N'Apple Watch Series 7', 9499000.00, 150, 'C3', 'B1','images/P41AppleWatchSeries7.jpg'),
('P42', N'Samsung Galaxy Buds 2', 3499000.00, 200, 'C3', 'B2','images/P42SamsungGalaxyBuds2.jpg'),
('P43', N'Sony WH-1000XM4', 7999000.00, 120, 'C3', 'B3','images/P43SonyWH1000XM4.jpg'),
('P44', N'Dell UltraSharp Monitor', 9999000.00, 70, 'C3', 'B4','images/P44DellUltraSharpMonitor.jpg'),
('P45', N'HP Omen Headset', 4599000.00, 90, 'C3', 'B5','images/P45HPOmenHeadset.jpg'),
('P46', N'Lenovo Smart Clock', 2999000.00, 110, 'C3', 'B6','images/P46LenovoSmartClock.jpg'),
('P47', N'Asus ROG Strix Monitor', 8499000.00, 60, 'C3', 'B7','images/P47AsusROGStrixMonitor.jpg'),
('P48', N'Acer Predator X34', 11999000.00, 50, 'C3', 'B8','images/P48AcerPredatorX34.jpg'),
('P49', N'MSI Optix MAG272CQR', 6499000.00, 80, 'C3', 'B9','images/P49MSIOptixMAG272CQR.jpg'),
('P50', N'Razer Kraken V3', 3999000.00, 100, 'C3', 'B10','images/P50RazerKrakenV3.jpg'),
('P51', N'Apple AirPods Pro', 6999000.00, 140, 'C3', 'B1','images/P51AppleAirPodsPro.jpg'),
('P52', N'Samsung T7 Portable SSD', 3499000.00, 130, 'C3', 'B2','images/P52SamsungT7PortableSSD.jpg'),
('P53', N'Sony XB33 Portable Speaker', 5599000.00, 90, 'C3', 'B3','images/P53SonyXB33PortableSpeaker.jpg'),
('P54', N'Dell Alienware Headset', 4999000.00, 75, 'C3', 'B4','images/P54DellAlienwareHeadset.jpg'),
('P55', N'HP Pavilion Webcam', 2199000.00, 110, 'C3', 'B5','images/P55HPPavilionWebcam.jpg'),
('P56', N'Lenovo Legion Gaming Mouse', 2799000.00, 120, 'C3', 'B6','images/P56LenovoLegionGamingMouse.jpg'),
('P57', N'Asus ROG Gaming Keyboard', 3499000.00, 95, 'C3', 'B7','images/P57AsusROGGamingKeyboard.jpg'),
('P58', N'Acer Nitro 5 Gaming Headset', 2999000.00, 130, 'C3', 'B8','images/P58AcerNitro5GamingHeadset.jpg'),
('P59', N'MSI MPG Artymis Monitor', 7999000.00, 85, 'C3', 'B9','images/P59MSIMPGArtymisMonitor.jpg'),
('P60', N'Razer DeathAdder V2', 2299000.00, 150, 'C3', 'B10','images/P60RazerDeathAdderV2.jpg');

-- Thêm chi tiết cho sản phẩm Điện Thoại
INSERT INTO ProductDetails (productDetailID, productID, description, specifications, warrantyPeriod)
VALUES 
('PD1', 'P1', N'iPhone 14 với màn hình OLED 6.1 inch, chip A15 Bionic, camera kép 12MP.', N'Màn hình: 6.1 inch; Chip: A15 Bionic; Camera: 12MP', '12 tháng'),
('PD2', 'P2', N'Samsung Galaxy S22 với màn hình AMOLED 6.2 inch, camera 64MP, pin 4000mAh.', N'Màn hình: 6.2 inch; Camera: 64MP; Pin: 4000mAh', '12 tháng'),
('PD3', 'P3', N'Sony Xperia 5 với màn hình OLED 6.1 inch, chip Snapdragon 865, camera 12MP.', N'Màn hình: 6.1 inch; Chip: Snapdragon 865; Camera: 12MP', '12 tháng'),
('PD4', 'P4', N'Xiaomi Mi 11 với màn hình AMOLED 6.81 inch, chip Snapdragon 888, camera 108MP.', N'Màn hình: 6.81 inch; Chip: Snapdragon 888; Camera: 108MP', '12 tháng'),
('PD5', 'P5', N'Google Pixel 6 với màn hình OLED 6.4 inch, chip Google Tensor, camera kép 50MP.', N'Màn hình: 6.4 inch; Chip: Google Tensor; Camera: 50MP', '12 tháng'),
('PD6', 'P6', N'OnePlus 9 Pro với màn hình Fluid AMOLED 6.7 inch, chip Snapdragon 888, camera Hasselblad.', N'Màn hình: 6.7 inch; Chip: Snapdragon 888; Camera: Hasselblad', '12 tháng'),
('PD7', 'P7', N'OPPO Find X3 Pro với màn hình AMOLED 6.7 inch, chip Snapdragon 888, camera kép 50MP.', N'Màn hình: 6.7 inch; Chip: Snapdragon 888; Camera: 50MP', '12 tháng'),
('PD8', 'P8', N'Vivo X60 Pro với màn hình AMOLED 6.56 inch, chip Snapdragon 870, camera 48MP.', N'Màn hình: 6.56 inch; Chip: Snapdragon 870; Camera: 48MP', '12 tháng'),
('PD9', 'P9', N'Asus ROG Phone 5 với màn hình AMOLED 6.78 inch, chip Snapdragon 888, pin 6000mAh.', N'Màn hình: 6.78 inch; Chip: Snapdragon 888; Pin: 6000mAh', '12 tháng'),
('PD10', 'P10', N'Nokia 8.3 với màn hình IPS LCD 6.81 inch, chip Snapdragon 765G, camera 64MP.', N'Màn hình: 6.81 inch; Chip: Snapdragon 765G; Camera: 64MP', '12 tháng'),
('PD11', 'P11', N'Realme GT với màn hình Super AMOLED 6.43 inch, chip Snapdragon 888, pin 4500mAh.', N'Màn hình: 6.43 inch; Chip: Snapdragon 888; Pin: 4500mAh', '12 tháng'),
('PD12', 'P12', N'Motorola Edge 20 Pro với màn hình OLED 6.7 inch, chip Snapdragon 870, camera 108MP.', N'Màn hình: 6.7 inch; Chip: Snapdragon 870; Camera: 108MP', '12 tháng'),
('PD13', 'P13', N'Huawei Mate 40 Pro với màn hình OLED 6.76 inch, chip Kirin 9000, camera 50MP.', N'Màn hình: 6.76 inch; Chip: Kirin 9000; Camera: 50MP', '12 tháng'),
('PD14', 'P14', N'LG Wing với màn hình P-OLED 6.8 inch xoay, chip Snapdragon 765G, camera 64MP.', N'Màn hình: 6.8 inch; Chip: Snapdragon 765G; Camera: 64MP', '12 tháng'),
('PD15', 'P15', N'Xiaomi Redmi Note 10 với màn hình Super AMOLED 6.43 inch, chip Snapdragon 678, camera 48MP.', N'Màn hình: 6.43 inch; Chip: Snapdragon 678; Camera: 48MP', '12 tháng'),
('PD16', 'P16', N'Realme 8 Pro với màn hình Super AMOLED 6.4 inch, chip Snapdragon 720G, camera 108MP.', N'Màn hình: 6.4 inch; Chip: Snapdragon 720G; Camera: 108MP', '12 tháng'),
('PD17', 'P17', N'Poco X3 Pro với màn hình IPS LCD 6.67 inch, chip Snapdragon 860, pin 5160mAh.', N'Màn hình: 6.67 inch; Chip: Snapdragon 860; Pin: 5160mAh', '12 tháng'),
('PD18', 'P18', N'Vivo V21 với màn hình AMOLED 6.44 inch, chip Dimensity 800U, camera 64MP.', N'Màn hình: 6.44 inch; Chip: Dimensity 800U; Camera: 64MP', '12 tháng'),
('PD19', 'P19', N'OPPO Reno5 với màn hình AMOLED 6.4 inch, chip Snapdragon 720G, camera 64MP.', N'Màn hình: 6.4 inch; Chip: Snapdragon 720G; Camera: 64MP', '12 tháng'),
('PD20', 'P20', N'Samsung Galaxy A32 với màn hình AMOLED 6.4 inch, camera 64MP, pin 5000mAh.', N'Màn hình: 6.4 inch; Camera: 64MP; Pin: 5000mAh', '12 tháng');

-- Thêm chi tiết cho sản phẩm Máy Tính
INSERT INTO ProductDetails (productDetailID, productID, description, specifications, warrantyPeriod)
VALUES 
('PD21', 'P21', N'Dell XPS 13 với màn hình 13.3 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 13.3 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD22', 'P22', N'HP Spectre x360 với màn hình 13.3 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 13.3 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD23', 'P23', N'Lenovo ThinkPad X1 với màn hình 14 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 14 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD24', 'P24', N'MacBook Air với màn hình Retina 13.3 inch, chip Apple M1, RAM 8GB, SSD 256GB.', N'Màn hình: 13.3 inch; Chip: Apple M1; RAM: 8GB; SSD: 256GB', '24 tháng'),
('PD25', 'P25', N'Asus ZenBook 14 với màn hình 14 inch, chip AMD Ryzen 7, RAM 16GB, SSD 512GB.', N'Màn hình: 14 inch; Chip: AMD Ryzen 7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD26', 'P26', N'Acer Swift 3 với màn hình 14 inch, chip Intel Core i5, RAM 8GB, SSD 512GB.', N'Màn hình: 14 inch; Chip: Intel Core i5; RAM: 8GB; SSD: 512GB', '24 tháng'),
('PD27', 'P27', N'MSI GS66 Stealth với màn hình 15.6 inch, chip Intel Core i7, RAM 16GB, SSD 1TB.', N'Màn hình: 15.6 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 1TB', '24 tháng'),
('PD28', 'P28', N'Razer Blade 15 với màn hình 15.6 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD29', 'P29', N'Gigabyte AERO 15 với màn hình 15.6 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD30', 'P30', N'LG Gram 14 với màn hình 14 inch, chip Intel Core i5, RAM 8GB, SSD 256GB.', N'Màn hình: 14 inch; Chip: Intel Core i5; RAM: 8GB; SSD: 256GB', '24 tháng'),
('PD31', 'P31', N'Asus ROG Zephyrus G14 với màn hình 14 inch, chip AMD Ryzen 9, RAM 16GB, SSD 1TB.', N'Màn hình: 14 inch; Chip: AMD Ryzen 9; RAM: 16GB; SSD: 1TB', '24 tháng'),
('PD32', 'P32', N'MacBook Pro với màn hình Retina 16 inch, chip Apple M1 Max, RAM 32GB, SSD 1TB.', N'Màn hình: 16 inch; Chip: Apple M1 Max; RAM: 32GB; SSD: 1TB', '24 tháng'),
('PD33', 'P33', N'Surface Laptop 4 với màn hình 13.5 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 13.5 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD34', 'P34', N'HP Envy 13 với màn hình 13.3 inch, chip Intel Core i7, RAM 8GB, SSD 256GB.', N'Màn hình: 13.3 inch; Chip: Intel Core i7; RAM: 8GB; SSD: 256GB', '24 tháng'),
('PD35', 'P35', N'Dell G5 15 với màn hình 15.6 inch, chip Intel Core i7, RAM 16GB, SSD 512GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i7; RAM: 16GB; SSD: 512GB', '24 tháng'),
('PD36', 'P36', N'Asus TUF Gaming F15 với màn hình 15.6 inch, chip Intel Core i5, RAM 8GB, SSD 512GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i5; RAM: 8GB; SSD: 512GB', '24 tháng'),
('PD37', 'P37', N'Lenovo IdeaPad 5 với màn hình 15.6 inch, chip AMD Ryzen 5, RAM 8GB, SSD 256GB.', N'Màn hình: 15.6 inch; Chip: AMD Ryzen 5; RAM: 8GB; SSD: 256GB', '24 tháng'),
('PD38', 'P38', N'Acer Nitro 5 với màn hình 15.6 inch, chip Intel Core i5, RAM 8GB, SSD 512GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i5; RAM: 8GB; SSD: 512GB', '24 tháng'),
('PD39', 'P39', N'HP Pavilion 15 với màn hình 15.6 inch, chip Intel Core i5, RAM 8GB, SSD 256GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i5; RAM: 8GB; SSD: 256GB', '24 tháng'),
('PD40', 'P40', N'Dell Inspiron 15 với màn hình 15.6 inch, chip Intel Core i3, RAM 4GB, SSD 128GB.', N'Màn hình: 15.6 inch; Chip: Intel Core i3; RAM: 4GB; SSD: 128GB', '24 tháng');

-- Thêm chi tiết cho sản phẩm Điện Tử
INSERT INTO ProductDetails (productDetailID, productID, description, specifications, warrantyPeriod)
VALUES 
('PD41', 'P41', N'Sony Bravia 55 inch với độ phân giải 4K, HDR, công nghệ X-Reality PRO.', N'Kích thước: 55 inch; Độ phân giải: 4K; Công nghệ: X-Reality PRO', '24 tháng'),
('PD42', 'P42', N'Samsung QLED 65 inch với độ phân giải 4K, HDR10+, công nghệ Quantum Dot.', N'Kích thước: 65 inch; Độ phân giải: 4K; Công nghệ: Quantum Dot', '24 tháng'),
('PD43', 'P43', N'LG OLED 55 inch với độ phân giải 4K, HDR10, công nghệ AI ThinQ.', N'Kích thước: 55 inch; Độ phân giải: 4K; Công nghệ: AI ThinQ', '24 tháng'),
('PD44', 'P44', N'Panasonic 49 inch với độ phân giải Full HD, công nghệ HDR, IPS LED.', N'Kích thước: 49 inch; Độ phân giải: Full HD; Công nghệ: HDR', '24 tháng'),
('PD45', 'P45', N'Philips 50 inch với độ phân giải 4K, công nghệ Ambilight, HDR10+.', N'Kích thước: 50 inch; Độ phân giải: 4K; Công nghệ: Ambilight', '24 tháng'),
('PD46', 'P46', N'Toshiba 43 inch với độ phân giải Full HD, công nghệ CEVO Engine.', N'Kích thước: 43 inch; Độ phân giải: Full HD; Công nghệ: CEVO Engine', '24 tháng'),
('PD47', 'P47', N'Sharp 50 inch với độ phân giải 4K, HDR10, công nghệ X4 Master Engine Pro II.', N'Kích thước: 50 inch; Độ phân giải: 4K; Công nghệ: X4 Master Engine Pro II', '24 tháng'),
('PD48', 'P48', N'Sony HT-X8500 Soundbar với âm thanh Dolby Atmos, loa siêu trầm tích hợp.', N'Âm thanh: Dolby Atmos; Loa: Tích hợp loa siêu trầm', '12 tháng'),
('PD49', 'P49', N'Bose SoundLink Revolve+ với âm thanh 360 độ, kết nối Bluetooth.', N'Âm thanh: 360 độ; Kết nối: Bluetooth', '12 tháng'),
('PD50', 'P50', N'JBL Flip 5 với âm thanh mạnh mẽ, chống nước IPX7, thời lượng pin 12 giờ.', N'Âm thanh: Mạnh mẽ; Chống nước: IPX7; Pin: 12 giờ', '12 tháng'),
('PD51', 'P51', N'Apple Watch Series 6 với màn hình Retina Always-On, đo nồng độ oxy trong máu.', N'Màn hình: Retina Always-On; Tính năng: Đo nồng độ oxy trong máu', '12 tháng'),
('PD52', 'P52', N'Samsung Galaxy Watch 3 với màn hình Super AMOLED, theo dõi sức khỏe.', N'Màn hình: Super AMOLED; Tính năng: Theo dõi sức khỏe', '12 tháng'),
('PD53', 'P53', N'Garmin Forerunner 945 với GPS tích hợp, theo dõi hoạt động thể thao.', N'Tính năng: GPS tích hợp; Theo dõi: Hoạt động thể thao', '12 tháng'),
('PD54', 'P54', N'Fitbit Versa 3 với theo dõi nhịp tim, GPS, trợ lý giọng nói.', N'Tính năng: Theo dõi nhịp tim; GPS; Trợ lý giọng nói', '12 tháng'),
('PD55', 'P55', N'Amazfit GTR 2 với màn hình AMOLED, đo nồng độ oxy trong máu, theo dõi giấc ngủ.', N'Màn hình: AMOLED; Tính năng: Đo nồng độ oxy trong máu; Theo dõi giấc ngủ', '12 tháng'),
('PD56', 'P56', N'Sony WH-1000XM4 với chống ồn chủ động, âm thanh Hi-Res, thời lượng pin 30 giờ.', N'Tính năng: Chống ồn chủ động; Âm thanh: Hi-Res; Pin: 30 giờ', '12 tháng'),
('PD57', 'P57', N'Bose QuietComfort 35 II với chống ồn chủ động, kết nối Bluetooth, âm thanh chất lượng cao.', N'Tính năng: Chống ồn chủ động; Kết nối: Bluetooth', '12 tháng'),
('PD58', 'P58', N'Beats Studio3 với chống ồn chủ động, âm thanh chất lượng cao, thời lượng pin 22 giờ.', N'Tính năng: Chống ồn chủ động; Pin: 22 giờ', '12 tháng'),
('PD59', 'P59', N'JBL Charge 4 với âm thanh mạnh mẽ, chống nước IPX7, thời lượng pin 20 giờ.', N'Âm thanh: Mạnh mẽ; Chống nước: IPX7; Pin: 20 giờ', '12 tháng'),
('PD60', 'P60', N'Apple AirPods Pro với chống ồn chủ động, âm thanh không gian, kết nối Bluetooth.', N'Tính năng: Chống ồn chủ động; Âm thanh: Không gian; Kết nối: Bluetooth', '12 tháng');

-- Thêm Vai Trò
INSERT INTO Roles (roleID, roleName) VALUES 
('AD', 'Administrator'),
('US', 'User');

-- Thêm Người Dùng Mới
INSERT INTO Users (userID, fullName, password, roleID, status) 
VALUES 
('admin', N'Admin', '1', 'AD', 1),
('phuc', N'Phúc', '1', 'AD', 1),
('quan', N'Quân', '1', 'AD', 1),
('quang', N'Quang', '1', 'AD', 1),
('truong', N'Trường', '1', 'AD', 1),
('tuan', N'Tuấn', '1', 'AD', 1),
('huy', N'Huy', '1', 'AD', 1),
('bao', N'Bảo', '1', 'AD', 1),
('user1', N'Quang', '1', 'US', 1),
('user2', N'Trường', '1', 'US', 1),
('user3', N'Khang', '1', 'US', 1),
('user4', N'Tiên', '1', 'US', 1),
('user5', N'Linh', '1', 'US', 1);
