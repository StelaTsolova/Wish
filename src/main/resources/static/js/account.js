window.addEventListener('load', () => {
    let userEmail = localStorage.getItem('email');
    document.querySelector('h2').textContent = 'Information for ' + userEmail;
    document.querySelector('.change-pass').addEventListener('click', redirectChangePassword);

    loadInformation();
});

async function loadInformation() {
    const formElement = document.querySelector('form');
    formElement.addEventListener('submit', updateInformation)

    const response = await fetch('http://localhost:8080/users', {
        method: 'GET',
        // headers: {
        //     'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        // }
    });
    if (response.status == 200) {
        const data = await response.json();

        formElement.querySelector('[name="firstName"]').value = data.firstName;
        formElement.querySelector('[name="lastName"]').value = data.lastName;
        formElement.querySelector('[name="phoneNumber"]').value = data.phoneNumber;
    }
}

async function updateInformation(e) {
    e.preventDefault();
    removeErrorElements();

    const formData = getFormData(e.target);

    const response = await fetch('http://localhost:8080/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            firstName: formData.firstName,
            lastName: formData.lastName,
            phoneNumber: formData.phoneNumber,
        })
    });
    const data = await response.json();

    if (response.status == 400) {
        Object.entries(data).forEach(([key, val]) => {
            let errorElement = createErrorElement(val);
            let fieldElement = document.getElementById(key).parentNode;
            fieldElement.append(errorElement);
        });
    } else if (response.status == 200) {
        let newFirstName = Object.values(data)[0];
        localStorage.setItem('firstName', newFirstName);

        window.location = '/information';
    }
}

function getFormData(formElement) {
    const formData = new FormData(formElement);

    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const phoneNumber = formData.get('phoneNumber');

    return {firstName, lastName, phoneNumber};
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

function redirectChangePassword() {
    window.location = '/change';
}