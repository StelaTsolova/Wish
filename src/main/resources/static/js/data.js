window.addEventListener('load', () => {
    loadInfo();
})

async function loadInfo() {
    const formElement = document.querySelector('form');
    formElement.addEventListener('submit', createOrder)

    const response = await fetch('http://localhost:8080/users', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        }
    });
    if (response.status == 200) {
        const data = await response.json();

        let email = localStorage.getItem('email');
        formElement.querySelector('[name="firstName"]').value = data.firstName;
        formElement.querySelector('[name="lastName"]').value = data.lastName;
        formElement.querySelector('[name="email"]').value = email;
        formElement.querySelector('[name="phoneNumber"]').value = data.phoneNumber;
    }
}

async function createOrder(e) {
    e.preventDefault();
    removeErrorElements();

    const formData = getFormData(e.target);

    const response = await fetch('http://localhost:8080/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            'firstName': formData.firstName,
            'lastName': formData.lastName,
            'email': formData.email,
            'phoneNumber': formData.phoneNumber,
            'town': formData.town,
            'address': formData.address
        })
    });

    if (response.status == 400) {
        const data = await response.json();

        Object.entries(data).forEach(([key, val]) => {
            let errorElement = createErrorElement(val);
            let fieldElement = document.getElementById(key).parentNode;
            fieldElement.append(errorElement);
        });
    } else if (response.status == 200) {
        window.location = '/order';
    }
}

function getFormData(formElement) {
    const formData = new FormData(formElement);

    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const email = formData.get('email');
    const phoneNumber = formData.get('phoneNumber');
    const town = formData.get('town');
    const address = formData.get('address');

    return {firstName, lastName, email, phoneNumber, town, address};
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