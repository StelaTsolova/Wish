window.addEventListener('load', loadStats);

async function loadStats() {
    const response = await fetch('http://localhost:8080/statistics');

    if (response.status == 200) {
        const data = await response.json();

        let fragment = document.createDocumentFragment();
        fragment.append(createElement('h3', 'Authorized requests: ' + data.authRequests));
        fragment.append(createElement('h3', 'Anonymous requests: ' + data.anonymousRequests));
        fragment.append(createElement('h3', 'Totals requests: ' + data.totalRequests));

        document.querySelector('.mx-auto').replaceChildren(fragment);
    }
}

function createElement(tagName, text) {
    let h1Element = document.createElement(tagName);
    h1Element.classList.add('text-center');
    h1Element.classList.add('text-white');
    h1Element.classList.add('mt-5');
    h1Element.textContent = text;

    return h1Element;
}