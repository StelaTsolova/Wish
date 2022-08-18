let productId = sessionStorage.getItem('productId');
window.addEventListener('load', () => {
    if (productId !== null) {
        fillFields();
    }

    document.querySelector('#btn-size').addEventListener('click', addSizeFields);
    document.querySelector('#btn-img').addEventListener('click', addImgFields);
    document.querySelector('form').addEventListener('submit', onSubmit);
    document.querySelector('input[type="file"]').addEventListener('change', saveImg);
    document.querySelector('button[name="remove"]').addEventListener('click', removeImg);
});

async function fillFields() {
    const category = sessionStorage.getItem('categoryName');

    const response = await fetch('http://localhost:8080/products/' + productId);
    const data = await response.json();

    document.getElementById('name').value = data.name;
    document.getElementById('price').value = data.price;
    document.getElementById('category').value = category;
    document.getElementById('material').value = data.material;

    let firstQuantity = data.quantities.shift();
    document.getElementById('size').value = firstQuantity.sizeName;
    document.getElementById('quantity').value = firstQuantity.availableQuantity;

    data.quantities.forEach(q => addSizeFields(q.sizeName, q.availableQuantity));

    const picturesElement = document.getElementById('pictures');

    let pElement = document.createElement('p');
    pElement.textContent = 'Click on the photo they want to delete.';
    picturesElement.replaceChildren(pElement);

    let fragment = document.createDocumentFragment();
    data.imgUrls.map(u => createImgElement(u)).forEach(img => fragment.append(img));
    picturesElement.append(fragment);
    picturesElement.addEventListener('click', removePicture);

    document.querySelector('button[type="submit"]').textContent = 'Edit product';
}

async function removePicture(e) {
    if (e.target.tagName !== 'IMG') {
        return
    }

    let url = e.target.src;
    const response = await fetch('http://localhost:8080/pictures/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            'url': url
        })
    });

    if (response.status == 200) {
        e.target.remove();
    }
}

function onSubmit(e) {
    e.preventDefault();

    let buttonText = document.querySelector('button[type="submit"]').textContent;
    if (buttonText == 'Create product') {
        createProduct(e);
    } else if (buttonText == 'Edit product') {
        editProduct(e);
    }
}

async function createProduct(e) {
    removeErrorElements();

    const formData = new FormData(e.target);
    let name = formData.get('name');
    let price = formData.get('price');
    let category = formData.get('category');
    let material = formData.get('material');
    let sizes = formData.getAll('size');
    let quantities = formData.getAll('quantity');

    let availableQuantities = {};
    sizes.forEach((element, index) => {
        availableQuantities[element] = quantities[index];
    });

    const response = await fetch('http://localhost:8080/products/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name,
            price,
            'categoryName': category,
            material,
            'quantities': availableQuantities,
        })
    });
    const data = await response.json();

    if (response.status === 200) {
        sendImgs();
        e.target.reset();
        alert('Successfully created product.');
    } else {
        showsErrors(data);
    }
}

async function editProduct(e) {
    removeErrorElements();

    const formData = new FormData(e.target);
    let name = formData.get('name');
    let price = formData.get('price');
    let category = formData.get('category');
    let material = formData.get('material');
    let sizes = formData.getAll('size');
    let quantities = formData.getAll('quantity');

    let availableQuantities = {};
    sizes.forEach((element, index) => {
        availableQuantities[element] = quantities[index];
    });

    const response = await fetch('http://localhost:8080/products/edit?productId=' + productId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name,
            price,
            'categoryName': category,
            material,
            'quantities': availableQuantities,
        })
    });
    const data = await response.json();

    if (response.status == 200) {
        sendImgs();
        e.target.reset();
        alert('Successfully edited product.');
    } else {
        showsErrors(data);
    }
}

let imgs = {};

function saveImg(e) {
    let parentElement = e.target.parentNode;
    let key = parentElement.dataset.key;

    imgs[key] = e.target.files[0];
}

function removeSize(e) {
    e.target.parentNode.parentNode.remove();
}

function removeImg(e) {
    let parentElement = e.target.parentNode;
    let key = parentElement.dataset.key;

    delete imgs[key];
    parentElement.parentNode.parentNode.remove();
}

function addSizeFields(sizeName, quantity) {
    let divRow = document.createElement('div');
    divRow.classList.add('row');

    let divFirst = document.createElement('div');
    divFirst.classList.add('form-group');
    divFirst.classList.add('col-md-6');
    divFirst.classList.add('mb-3');

    let labelFirst = document.createElement('label');
    labelFirst.setAttribute('for', 'size');
    labelFirst.classList.add('text-white');
    labelFirst.classList.add('font-weight-bold');
    labelFirst.textContent = 'Size';
    divFirst.append(labelFirst);

    let inputFirst = document.createElement('input');
    inputFirst.id = 'size';
    inputFirst.name = 'size';
    inputFirst.type = 'text';
    inputFirst.classList.add('form-control');
    if (typeof sizeName !== 'object') {
        inputFirst.value = sizeName;
    }
    divFirst.append(inputFirst);

    let divSecond = document.createElement('div');
    divSecond.classList.add('form-group');
    divSecond.classList.add('col-md-5');
    divSecond.classList.add('mb-3');

    let labelSecond = document.createElement('label');
    labelSecond.setAttribute('for', 'quantity');
    labelSecond.classList.add('text-white');
    labelSecond.classList.add('font-weight-bold');
    labelSecond.textContent = 'Quantity';
    divSecond.append(labelSecond);

    let inputSecond = document.createElement('input');
    inputSecond.id = 'quantity';
    inputSecond.name = 'quantity';
    inputSecond.type = 'number';
    inputSecond.classList.add('form-control');
    if (quantity !== 'undefined') {
        inputSecond.value = quantity;
    }
    divSecond.append(inputSecond);

    let divThird = document.createElement('div');
    divThird.classList.add('form-group');
    divThird.classList.add('col-md-1');
    divThird.classList.add('mb-3');

    let button = document.createElement('button');
    button.textContent = 'X';
    button.addEventListener('click', removeSize);
    divThird.append(button);

    divRow.append(divFirst);
    divRow.append(divSecond);
    divRow.append(divThird);

    document.querySelector('#row-size').append(divRow);
}

let counter = 2;

function addImgFields() {
    let div = document.createElement('div');

    let label = document.createElement('label');
    label.setAttribute('for', 'picture');
    label.textContent = 'Select image:';
    div.append(label);

    let divRow = document.createElement('div');
    divRow.classList.add('row');
    div.append(divRow);

    let divFirst = document.createElement('div');
    divFirst.dataset.key = counter;
    divFirst.classList.add('form-group');
    divFirst.classList.add('col-md-6');
    divFirst.classList.add('mb-3');
    divRow.append(divFirst);

    let inputFirst = document.createElement('input');
    inputFirst.id = 'picture';
    inputFirst.name = 'picture'
    inputFirst.type = 'file';
    inputFirst.accept = 'image/*';
    inputFirst.addEventListener('change', saveImg);
    divFirst.append(inputFirst);

    let divSecond = document.createElement('div');
    divSecond.classList.add('form-group');
    divSecond.classList.add('col-md-6');
    divSecond.classList.add('mb-3');
    divRow.append(divSecond);

    let button = document.createElement('button');
    button.name = 'remove';
    button.type = 'button';
    button.textContent = 'Remove';
    button.addEventListener('click', removeImg);
    divSecond.append(button);

    counter++;
    document.querySelector('#row-image').append(div);
}

function createImgElement(url) {
    let img = document.createElement('img');
    img.src = url;
    img.width = 100;
    img.height = 100;
    img.setAttribute('margin-right', '10px');

    return img;
}

function createErrorElement(message) {
    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.textContent = message;

    return pElement;
}

function removeErrorElements() {
    document.querySelectorAll('p').forEach(p => p.remove());
}

function sendImgs() {
    let formData = Object.values(imgs).map(img => {
        let formData = new FormData();
        formData.append('multipartFile', img);

        return formData;
    })
    Promise.all(
        formData.map(f => {
            fetch('http://localhost:8080/pictures/add?folderName=' + data.productId, {
                method: 'POST',
                body: f
            });
        })
    );
    imgs = {};
}

function showsErrors(data) {
    Object.entries(data).forEach(([key, val]) => {
        let errorElement = createErrorElement(val);
        let fieldElement = document.getElementById(key).parentNode;
        fieldElement.append(errorElement);
    });
}