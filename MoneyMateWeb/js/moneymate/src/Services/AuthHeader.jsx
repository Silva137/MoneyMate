export  function authHeader() {
    const user = JSON.parse(sessionStorage.getItem('user'));

    if (user && user.access_token) {
        return { Authorization: 'Bearer ' + user.access_token }
    } else {
        return {}
    }
}