window.addEventListener('load', () => {
    let userEmail = localStorage.getItem('email');
    document.querySelector('h2').textContent = 'Information for ' + userEmail;

    document.querySelector('form').addEventListener('submit', changePassword);
});


async function changePassword(e) {
    e.preventDefault();

    let errorElement = document.querySelector('p');
    if (errorElement !== null) {
        errorElement.remove();
    }

    const formData = new FormData(e.target);
    const password = formData.get('password');
    const newPassword = formData.get('newPassword');

    const rowElement = document.querySelector('.row');
    if(password == '' || newPassword == ''){
        rowElement.append(createErrorElement('All fields are required!'));
    }

    const response = await fetch('http://localhost:8080/users/change', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            password,
            newPassword
        })
    })

    if (response.status == 400) {
        const data = await response.json();

        let message = Object.values(data)[0];
        rowElement.append(createErrorElement(message));
    } else if(response.status == 200) {
        window.location = '/information';
    }
}

function createErrorElement(message) {
    let pElement = document.createElement('p');
    pElement.style.color = 'red';
    pElement.textContent = message;

    return pElement;
}