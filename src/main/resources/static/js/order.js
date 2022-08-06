window.addEventListener('load', () =>{
    document.querySelector('h2').textContent  = localStorage.getItem('firstName');
});