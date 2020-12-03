function refillSignUp(userName, email) {
    document.getElementById("userName").setAttribute('value', userName);
    document.getElementById("email").setAttribute('value', email);
}

function refillSignIn(email) {
    document.getElementById("email").setAttribute('value', email);
}

function refillStaffId(staffId) {
    document.getElementById("staffId").setAttribute('value', staffId);
}