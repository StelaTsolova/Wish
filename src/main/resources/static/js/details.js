window.addEventListener('load', () => {
    sessionStorage.removeItem('productSize');
    loadData();
});
const productId = sessionStorage.getItem('productId');

async function loadData() {
    const response = await fetch('http://localhost:8080/products/' + productId);
    if (response.status === 200) {
        const data = await response.json();

        const previewElement = document.querySelector('.preview');
        previewElement.replaceChildren(createPreview(data.imgUrls));
        previewElement.append(createPreviewThumbnail(data.imgUrls));

        document.querySelector('.product-title').textContent = data.name;
        document.querySelector('.price').textContent = data.price + ' lv';
        document.querySelector('.product-description span').textContent = data.material;

        const sizesElement = document.querySelector('.sizes');
        sizesElement.replaceChildren(...data.quantities.map(q => createSpan(q)));
        sizesElement.addEventListener('click', choseSize);
    }

    let addButton = document.querySelector('.add-to-cart');
    let likeButton = document.querySelector('.like');
    if (role === 'USER') {
        addButton.addEventListener('click', addToCart);
        likeButton.addEventListener('click', addToWishlist);
    } else if (role === 'ADMIN') {
        addButton.textContent = 'Edit';
        addButton.addEventListener('click', redirectEdit);

        likeButton.replaceChildren();
        likeButton.textContent = 'Delete';
        likeButton.addEventListener('click', deleteProduct);
    } else {
        document.querySelector('.action').replaceChildren();
    }
}

function choseSize(e) {
    if (e.target.tagName !== 'SPAN') {
        return;
    }

    let currentSize = sessionStorage.productSize;
    if (currentSize !== undefined) {
        let currentElement = e.target.parentNode.querySelector('span[value="' + currentSize + '"]');
        currentElement.style.background = '#fff';
    }

    e.target.style.background = '#E8DAEF';

    sessionStorage.setItem('productSize', e.target.textContent);
}

async function addToCart(e) {
    let currentSize = sessionStorage.getItem('productSize');
    let errorMessage = document.querySelector('.error-class');

    if (currentSize == null) {
        errorMessage.style.visibility = 'visible';
        return;
    } else {
        errorMessage.style.visibility = 'hidden';
    }

    const response = await fetch('http://localhost:8080/cart/products', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
        },
        body: JSON.stringify({
            productId,
            'productSize': currentSize
        })
    });

    if (response.status === 200) {
        alert('Product is successfully added to cart.');
    } else {
        alert('Insufficient availability.');
    }
}

async function addToWishlist() {
    const response = await fetch('http://localhost:8080/users/wishlist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            productId
        })
    });

    if (response.status == 200) {
        alert('Product is successfully added to wishlist.');
    }
}

function createPreview(imgUrls) {
    let divPreviewPic = document.createElement('div');
    divPreviewPic.classList.add('preview-pic');
    divPreviewPic.classList.add('tab-content');

    let counter = 1;
    imgUrls.map(url => {
        let div = document.createElement('div');
        div.classList.add('tab-pane');
        div.id = 'pic-' + counter;
        if (counter == 1) {
            div.classList.add('active');
        }

        let img = document.createElement('img');
        img.src = url;
        div.append(img);

        counter++;
        return div;
    }).forEach(div => divPreviewPic.append(div));

    return divPreviewPic;
}

function createPreviewThumbnail(imgUrls) {
    let ulPreviewThumbnail = document.createElement('ul');
    ulPreviewThumbnail.classList.add('preview-thumbnail');
    ulPreviewThumbnail.classList.add('nav');
    ulPreviewThumbnail.classList.add('nav-tabs');

    let counter = 1;
    imgUrls.map(url => {
        let liElement = document.createElement('li');
        if (counter == 1) {
            liElement.classList.add('active');
        }

        let aElement = document.createElement('a');
        aElement.dataset.target = '#pic-' + counter;
        aElement.dataset.toggle = 'tab';
        liElement.append(aElement);

        let imgElement = document.createElement('img');
        imgElement.src = url;
        aElement.append(imgElement);

        counter++;
        return liElement;
    }).forEach(li => ulPreviewThumbnail.append(li));

    return ulPreviewThumbnail;
}

function createSpan(quantity) {
    let span = document.createElement('span');
    span.classList.add('size');
    span.dataset.toggle = 'tooltip';
    span.setAttribute('value', quantity.sizeName);
    span.textContent = quantity.sizeName;

    return span;
}

function redirectEdit() {
    window.location = "/product";
}

async function deleteProduct() {
    const response = await fetch('http://localhost:8080/products?id=' + productId, {
        method: 'DELETE',
    });

    if (response.status === 204) {
        window.location = '/products';
    }
}