document.getElementById('home').addEventListener('click', () => {
    window.location = '/home';
});
addProduct();

const rightNavbar = document.querySelector('.navbar-right');
rightNavbar.addEventListener('click', onClick);

const email = localStorage.getItem('email');
const role = localStorage.getItem('role');
if (email != null) {
    if (role === 'USER') {
        rightNavbar.append(createOption('Wishlist', '/images/icon/heart.png'));
        rightNavbar.append(createOption('Cart', '/images/icon/cart.png'));
    } else if (role === 'ADMIN') {
        rightNavbar.replaceChildren();
        rightNavbar.append(createOption('Create product'));
        rightNavbar.append(createOption('Create user'));
        rightNavbar.append(createOption('Statistic'));
    }
    rightNavbar.append(createOption('Logout'));
}

async function addProduct() {
    const dropdownMenu = document.querySelector(".dropdown-menu");
    $('.dropdown a').on("click", function (e) {
        $(this).next('ul').toggle();
        e.stopPropagation();
        e.preventDefault();
    });

    const response = await fetch('http://localhost:8080/categories');
    if(response.status === 200){
        const data = await response.json();

        let fragment = document.createDocumentFragment();
        data.map(d => createLiElement(d)).forEach(li => fragment.append(li));

        dropdownMenu.replaceChildren(fragment);
    }
    dropdownMenu.addEventListener('click', redirectProducts);
}

function createLiElement(data) {
    let liElement = document.createElement('li');

    let aElement = document.createElement('a');
    aElement.textContent = data.name;
    liElement.append(aElement);

    return liElement;
}

function redirectProducts(e) {
    if (e.target.tagName === 'UL') {
        return;
    }

    let categoryName = e.target.textContent;

    sessionStorage.setItem('categoryName', categoryName);
    window.location = '/products';
}

function createOption(text, path) {
    let liElement = document.createElement('li');

    let aElement = document.createElement('a');
    aElement.href = '/';
    aElement.textContent = text;

    if (path != undefined) {
        let imgElement = document.createElement('img');
        imgElement.src = path;
        aElement.insertBefore(imgElement, aElement.firstChild);
    }

    liElement.append(aElement);

    return liElement;
}

function onClick(e) {
    e.preventDefault();
    let element = e.target;
    if (element.tagName === 'IMG') {
        element = element.parentNode;
    }

    switch (element.textContent) {
        case 'Account':
            account();
            break;
        case 'Wishlist':
            window.location = '/wishlist';
            break;
        case 'Cart':
            window.location = '/cart';
            break;
        case 'Logout':
            logout();
            break;
        case 'Create product':
            window.location = '/product';
            break;
        case 'Create user':
            window.location = '/user';
            break;
        case 'Statistic':
            window.location = '/statistic';
            break;
    }
}

function account() {
    if (email == null) {
        window.location = '/login';
    } else {
        window.location = '/information';
    }
}

async function logout() {
   let response = await fetch('http://localhost:8080/logout', {
        method:'POST'
    });
console.log(response.status)
    // localStorage.removeItem('accessToken');
    localStorage.removeItem('email');
    localStorage.removeItem('firstName');
    localStorage.removeItem('role');
    localStorage.removeItem('productId');
    sessionStorage.removeItem('productId');
    sessionStorage.removeItem('categoryName');
}
