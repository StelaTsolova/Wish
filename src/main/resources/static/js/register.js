window.addEventListener('load', () => {
    document.querySelector('form').addEventListener('submit', registerUser);
});

async function registerUser(e) {
    e.preventDefault();
    removeErrorElements();

    const formData = getFormData(e.target);

    if (formData.password !== formData.confirmPassword) {
        let fieldElement = document.getElementById('confirmPassword').parentNode;
        let errorElement = createErrorElement('Passwords should match.');

        fieldElement.append(errorElement);
        return;
    }

    const response = await fetch('http://localhost:8080/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            'firstName': formData.firstName,
            'lastName': formData.lastName,
            'email': formData.email,
            'phoneNumber': formData.phoneNumber,
            'password': formData.password,
        })
    });

    if (response.status == 400) {
        const data = await response.json();

        Object.entries(data).forEach(([key, val]) => {
            let errorElement = createErrorElement(val);
            let fieldElement = document.getElementById(key).parentNode;
            fieldElement.append(errorElement);
        });
    } else if(response.status == 200){
        window.location = '/login';
    }
}

function getFormData(formElement) {
    const formData = new FormData(formElement);

    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const email = formData.get('email');
    const phoneNumber = formData.get('phoneNumber');
    const password = formData.get('password');
    const confirmPassword = formData.get('confirmPassword');

    return {firstName, lastName, email, phoneNumber, password, confirmPassword};
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
