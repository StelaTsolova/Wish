window.addEventListener('load', () => {
    let itemsElement = document.querySelector('.items');
    document.querySelector('button').addEventListener('click', redirectData);
console.log(document.querySelector('button'))
    loadProducts(itemsElement);
})

async function loadProducts(itemsElement) {
    const response = await fetch('http://localhost:8080/cart/products', {
        method: 'GET',
        // headers: {
        //     'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        // }
    });
    const data = await response.json();

    if (response.status == 200) {
        let fragment = document.createDocumentFragment();
        data.map(d => createDivElement(d)).forEach(div => fragment.append(div));

        itemsElement.replaceChildren(fragment);
        totalSum();
    }
}

function createDivElement(data) {
    let divProduct = document.createElement('div');
    divProduct.classList.add('product');
    divProduct.innerHTML = `<div class="row">
                                    <div class="col-md-3">
                                        <img class="img-fluid mx-auto d-block image" src="${data.productDto.imgUrl}"
                                        width="200" height="200">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="info">
                                            <div class="row">
                                                <div class="col-md-5 product-name">
                                                    <div class="product-name">
                                                        <a href="#">${data.productDto.name}</a>
                                                        <div class="product-info">
                                                            <div>Size: <span class="value">${data.size}</span></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4 quantity">
                                                    <label for="quantity">Quantity:</label>
                                                    <input id="quantity" type="number" value="${data.quantity}"
                                                    min="1" max="${data.availableQuantity}"
                                                    data-id="${data.id}" class="form-control quantity-input">
                                                </div>
                                                <div class="col-md-3 price">
                                                   <span data-price="${data.productDto.price}">${data.productDto.price * data.quantity} </span> 
                                                   <span>lv</span>
                                                </div>
                                                <div class="col-md-2 remove">                            
                                                    <a href="" class="btn btn-light btn-round"
                                                       data-abc="true" data-id="${data.id}">Remove</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`

    divProduct.querySelector('#quantity').addEventListener('change', updateQuantity)
    divProduct.querySelector('.remove .btn').addEventListener('click', remove);

    return divProduct;
}

async function updateQuantity(e) {
    let quantity = e.target.value;
    let productId = e.target.dataset.id;

    const response = await fetch('http://localhost:8080/cart/' + productId, {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json',
            // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            quantity
        })
    });

    if (response.status == 200) {
        let spanPriceElement = e.target.parentNode.parentNode.querySelector('.price').firstElementChild;
        let priceForProduct = spanPriceElement.dataset.price;
        spanPriceElement.textContent = quantity * priceForProduct;

        totalSum();
    }
}

async function remove(e) {
    e.preventDefault();

    let productId = e.target.dataset.id;
    const response = await fetch('http://localhost:8080/cart/' + productId, {
        method: 'DELETE',
        headers: {
            // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    });
    if (response.status == 204) {
        e.target.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.remove();
        totalSum();
    }
}

function totalSum() {
    let prices = Array.from(document.querySelectorAll('.price :first-child'))
        .map(e => parseInt(e.textContent));
    let sum = prices.reduce((a, b) => a + b, 0);

    document.getElementById('total-price').textContent = sum + ' lv';
}

function redirectData() {
    window.location = '/data';
}