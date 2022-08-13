INSERT INTO CATEGORIES(NAME)
VALUES ('Shoes');

INSERT INTO PRODUCTS(CATEGORY_ID, NAME, PRICE)
VALUES (1, 'Sneakers', 50),
       (1, 'Slippers', 20);

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

INSERT INTO SIZES(NAME)
VALUES ('36'),
       ('38'),
       ('40');

INSERT INTO QUANTITIES(AVAILABLE_QUANTITY, PRODUCT_ID, SIZE_ID)
VALUES (10, 1, 1),
       (1, 1, 2),
       (7, 1, 3),
       (2, 2, 3);