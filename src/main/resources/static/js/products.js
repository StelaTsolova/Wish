window.addEventListener('load', loadProducts);

async function loadProducts() {
    const divElement = document.querySelector('.mt-5');
    const categoryName = sessionStorage.getItem('categoryName');

    const response = await fetch('http://localhost:8080/products/category?name=' + categoryName);
    if(response.status === 200){
        const data = await response.json();

        let fragment = document.createDocumentFragment();
        data.map(d => createDivElement(d)).forEach(div => fragment.append(div));

        divElement.replaceChildren(fragment);
    }

    divElement.addEventListener('click', redirectDetails);
}

export function createDivElement(data) {
    let firstDiv = document.createElement('div');
    firstDiv.classList.add('col-sm-4');

    let secondDiv = document.createElement('div');
    secondDiv.classList.add('cart');
    secondDiv.dataset.id = data.id;
    firstDiv.append(secondDiv);

    let img = document.createElement('img');
    img.src = data.imgUrl;
    img.classList.add('card-img-top');
    secondDiv.append(img);

    let thirdDiv = document.createElement('div');
    thirdDiv.classList.add('card-body', 'pt-0', 'px-0');
    secondDiv.append(thirdDiv);

    let h5 = document.createElement('h5');
    h5.classList.add('mb-0');
    h5.textContent = data.name;
    thirdDiv.append(h5);

    let small = document.createElement('small');
    small.classList.add('text-muted', 'mt-1');
    small.textContent = data.price + ' lv';
    thirdDiv.append(small);

    return firstDiv;
}

function redirectDetails(e) {
    let element = e.target.parentNode;

    if (!element.classList.contains('cart')) {
        element = element.parentNode;
    }
    if (!element.classList.contains('cart')) {
        return;
    }

    sessionStorage.setItem('productId', element.dataset.id);
    window.location = "/details";
}