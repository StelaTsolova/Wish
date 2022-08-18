window.addEventListener('load', () => {
    document.querySelector('form').addEventListener('submit', createUser);
});

async function createUser(e) {
    e.preventDefault();
    removeErrorElements();

    const formData = getFormData(e.target);

    const response = await fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            'firstName': formData.firstName,
            'lastName': formData.lastName,
            'email': formData.email,
            'phoneNumber': formData.phoneNumber,
            'password': formData.password,
            'role': formData.role
        })
    });

    if (response.status === 400) {
        const data = await response.json();

        Object.entries(data).forEach(([key, val]) => {
            let errorElement = createErrorElement(val);
            let fieldElement = document.getElementById(key).parentNode;
            fieldElement.append(errorElement);
        });
    } else if (response.status === 200) {
        e.target.reset();
        alert('Successfully create user.');
    }
}

function getFormData(formElement) {
    const formData = new FormData(formElement);

    const firstName = formData.get('firstName');
    const lastName = formData.get('lastName');
    const email = formData.get('email');
    const phoneNumber = formData.get('phoneNumber');
    const role = formData.get('role');
    const password = formData.get('password');

    return {firstName, lastName, email, phoneNumber, role, password};
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