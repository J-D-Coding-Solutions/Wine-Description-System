function logout() {
    fetch('/api/user/logout', {
        method: 'GET',
    })
        .then(response => response.json())
        .then(data => {
            // Handle logout success (redirect, show message, etc.)
            console.log(data.message);
            // Optionally, redirect to login page or home
            window.location.href = '/'; // or '/' for home page
        })
        .catch(error => console.error('Error logging out:', error));
}