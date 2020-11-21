const username_min_length = 2;
const username_max_length = 20;
const password_min_length = 8;

function validate(form) {
    fail = validateUserName(form.username.value);
    fail = validateEmail(form.email.value);
    fail = validatePassword(form.password.value);
    fail = validateRePassword(form.password.value, form.re_password.value);
    return fail == "";
}

function validateUserName(username) {
    if (username == "") return "Username field cannot be empty\n";
    if (/[^a-zA-Z0-9]/.test(username)) return "Username must contain only letters and numbers\n";
    if (!/^[a-zA-Z]/.test(username)) return "Username must a letter as first character\n";
    if (username.length < username_min_length) return "Username must contain at least " + username_min_length + " characters\n";
    if (username.length > username_max_length) return "Username must not contain more than " + username_max_length + " characters\n";
    return "";
}

function validateEmail(email) {
    if (email == "") return "Email address field cannot be empty\n";
    if (!/\S+@\S+\.\S+/.test(email)) return "Invalid email address\n";
    return "";
}

function validatePassword(pass) {
    if (pass == "") return "Passwords field cannot be empty\n";
    if (!/[a-z]/.test(pass)) return "Passwords must contain at least a lowercase letter\n";
    if (!/[A-Z]/.test(pass)) return "Passwords must contain at least an uppercase letter\n";
    if (!/[0-9]/.test(pass)) return "Passwords must contain at least a number\n";
    if (pass.length < password_min_length) return "Passwords must be at least " + password_min_length + " characters\n";
    return "";
}

function validateRePassword(pass, rePass) {
    if (rePass == "") return "Re-enter passwords field cannot be empty\n";
    if (pass != rePass) return "Passwords are not matched\n";
    return "";
}

function checkName(element, errorId) {
    document.getElementById(errorId).innerHTML = validateUserName(element.value);
}

function checkEmail(element, errorId) {
    document.getElementById(errorId).innerHTML = validateEmail(element.value);
}

function checkPassword(element, errorId) {
    document.getElementById(errorId).innerHTML = validatePassword(element.value);
}

function checkRePassword(formId, errorId) {
    form = document.getElementById(formId);
    document.getElementById(errorId).innerHTML = validateRePassword(form.password.value, form.re_password.value);
}