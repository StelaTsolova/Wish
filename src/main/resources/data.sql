INSERT INTO CATEGORIES(NAME)
VALUES ('Shoes'),
       ('Sunglasses');

INSERT INTO PRODUCTS(CATEGORY_ID, NAME, PRICE)
VALUES (1, 'maratonki', 50),
       (1, 'japanki', 20),
       (2, 'krygli ochila', 30);

INSERT INTO PICTURES(FOLDER_NAME, PUBLIC_ID, URL, PRODUCT_ID)
VALUES ('1', '1/01_5904248494766_rz__p27prl',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648041/1/01_5904248494766_rz__p27prl.jpg', '1'),
       ('1', '1/02_5904248494766_rz__y0lbbe',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648037/1/02_5904248494766_rz__y0lbbe.jpg', '1'),
       ('1', '1/03_5904248494766_rz__i5jujl',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648034/1/03_5904248494766_rz__i5jujl.jpg', '1'),
       ('1', '1/06_5904248494766_rz__smagz2',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648030/1/06_5904248494766_rz__smagz2.jpg', '1'),
       ('2', '2/0000208693059_01_kt_gtvmgo',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648582/2/0000208693059_01_kt_gtvmgo.jpg', '2'),
       ('2', '2/0000208693059_04_kt_sxv1ce',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648580/2/0000208693059_04_kt_sxv1ce.jpg', '2'),
       ('2', '2/0000208693059_02_kt_nvrg1d',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648579/2/0000208693059_02_kt_nvrg1d.jpg', '2');

-- INSERT INTO PRODUCTS_PICTURES(PRODUCT_ID, PICTURES_ID)
-- VALUES (1, 1),
--  (1, 2),
--  (1, 3),
--  (1, 4),
--  (2, 5),
--  (2, 6),
--  (2, 7);

-- INSERT INTO PRODUCT_IMG_URLS(PRODUCT_ID, IMG_URLS)
-- VALUES (1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQ6QmfP-KBgEba3cwMqWh8iZ7mZijHPcEwoA&usqp=CAU'),
--        (1,
--         'https://maksoft.net/img_preview.php?image_file=web/images/upload/maksoft/web_images.png&img_width=300&ratio=strict'),
--        (1, 'https://снимки.com/wp-content/uploads/2021/09/zalezi1-480x360.jpg'),
--        (2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQ6QmfP-KBgEba3cwMqWh8iZ7mZijHPcEwoA&usqp=CAU'),
--        (3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQ6QmfP-KBgEba3cwMqWh8iZ7mZijHPcEwoA&usqp=CAU');

INSERT INTO SIZES(NAME)
VALUES ('XS'),
       ('S'),
       ('M'),
       ('L'),
       ('XL');

INSERT INTO QUANTITIES(AVAILABLE_QUANTITY, PRODUCT_ID, SIZE_ID)
VALUES (10, 1, 1),
       (2, 2, 1),
       (1, 1, 3),
       (7, 1, 4),
       (6, 3, 5);
