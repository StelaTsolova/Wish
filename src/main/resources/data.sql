INSERT INTO CATEGORIES(NAME)
VALUES ('Shoes');

INSERT INTO PRODUCTS(CATEGORY_ID, NAME, PRICE)
VALUES (1, 'Sneakers', 50),
       (1, 'Slippers', 20);

INSERT INTO PICTURES(FOLDER_NAME, PUBLIC_ID, URL, PRODUCT_ID)
VALUES ('1', '1/01_5904248494766_rz__rcsjwo',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1660847832/1/01_5904248494766_rz__rcsjwo.jpg', '1'),
       ('1', '1/03_5904248494766_rz__we2ixt',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1660847858/1/03_5904248494766_rz__we2ixt.jpg', '1'),
       ('1', '1/06_5904248494766_rz__cruino',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1660847858/1/06_5904248494766_rz__cruino.jpg', '1'),
       ('1', '1/02_5904248494766_rz__ccs0jw',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1660847832/1/02_5904248494766_rz__ccs0jw.jpg', '1'),
       ('2', '2/0000208693059_01_kt_gtvmgo',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1659648582/2/0000208693059_01_kt_gtvmgo.jpg', '2'),
       ('2', '2/0000208693059_04_kt_as0mig',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1661031224/2/0000208693059_04_kt_as0mig.jpg', '2'),
       ('2', '2/0000208693059_02_kt_zbmgqy',
        'https://res.cloudinary.com/dj0dxejrk/image/upload/v1661031224/2/0000208693059_02_kt_zbmgqy.jpg', '2');

INSERT INTO SIZES(NAME)
VALUES ('36'),
       ('38'),
       ('40');

INSERT INTO QUANTITIES(AVAILABLE_QUANTITY, PRODUCT_ID, SIZE_ID)
VALUES (10, 1, 1),
       (1, 1, 2),
       (7, 1, 3),
       (2, 2, 3);