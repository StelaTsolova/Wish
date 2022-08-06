window.addEventListener('load', () => {
    document.querySelector('form').addEventListener('submit', loginUser);
    document.querySelector('.register').addEventListener('click', () =>{
        window.location = '/register';
    });
});

async function loginUser(e) {
    e.preventDefault();

    const errorElement = document.querySelector('p');

    const formData = new FormData(e.target);
    const email = formData.get('email');
    const password = formData.get('password');

    if (email == '' || password == '') {
        errorElement.style.visibility = 'visible';
        return;
    }

    const response = await fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email,
            password
        })
    });

    if (response.status == 200) {
        const data = await response.json()

        let role = data.roles[0];
        role = role.substring(5, role.length);

        localStorage.setItem("accessToken", data.token);
        localStorage.setItem("email", data.email);
        localStorage.setItem("firstName", data.firstName);
        localStorage.setItem("role", role);

        window.location = '/home';
    } else {
        errorElement.style.visibility = 'visible';
    }
}