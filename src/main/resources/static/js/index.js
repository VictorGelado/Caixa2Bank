const input = document.getElementById('password');
const viewPassword = document.getElementById('view-password');


viewPassword.addEventListener('click', () => {
	let attr = input.getAttribute('type');
	
	if ('password' == attr) {
		input.type = 'text';
	} else {
		input.type = 'password';
	}
})