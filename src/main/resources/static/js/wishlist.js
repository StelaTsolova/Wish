import {createDivElement} from "./products.js";

window.addEventListener('load', () => {
    loadProduct();
    document.querySelector('.user-firstName').textContent = localStorage.getItem('firstName');
});

async function loadProduct() {
    const divElement = document.querySelector('.mt-5');

    const response = await fetch('http://localhost:8080/users/wishlist', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    });

    if (response.status == 200) {
        const data = await response.json();

        let fragment = document.createDocumentFragment();
        data.map(d => createDivElement(d)).forEach(div => fragment.append(div));

        divElement.replaceChildren(fragment);
    }
}