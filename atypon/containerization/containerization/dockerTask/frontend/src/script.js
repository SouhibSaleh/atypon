localStorage.setItem("username", "");
localStorage.setItem("password", "");

document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();
    if (event.submitter.id === 'login') {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const data = {
            username: username,
            password: password
        };
        console.log(username)
        console.log(password)
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);

        fetch('http://localhost:8081/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                console.log('Server response:', result);
                document.querySelector('h1').textContent = result.message;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
    else {
        const username = localStorage.getItem("username");
        const password = localStorage.getItem("password");
        const data = {
            username: username,
            password: password
        };
        fetch('http://localhost:5000/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                console.log('All records from Python:', result);
                let records = result.map(user => `<p>${user.username}: ${user.password}</p>`).join('');
                document.querySelector('h1').innerHTML = records;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
});
