const username_min_length = 2;
const username_max_length = 20;
const password_min_length = 8;

function validate(form) {
    checkName(form.userName, "userNameError");
    checkEmail(form.email, "emailError");
    checkPassword(form.password, "passwordError");
    checkRePassword(form.id, "rePasswordError");
    fail = document.getElementById('userNameError').innerHTML;
    fail += document.getElementById('emailError').innerHTML;
    fail += document.getElementById('passwordError').innerHTML;
    fail += document.getElementById('rePasswordError').innerHTML;
    console.log("fail: " + fail);
    return fail == "";
}

function checkName(elementId, errorId) {
    document.getElementById(errorId).innerHTML = validateUserName(elementId.value);
}

async function checkEmail(elementId, errorId) {
    console.log(elementId.value);
    invalidEmail = validateEmail(elementId.value);
    if (invalidEmail == "") {
        invalidEmail = await findRegisteredEmail(elementId.name + "=" + elementId.value);
    }
    document.getElementById(errorId).innerHTML = invalidEmail;
}

function findRegisteredEmail(args) {
    return new Promise((resolve) => {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "FindRegisterUser", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send(args);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                resolve(this.responseText);
            }
        };
    });
}

function checkPassword(elementId, errorId) {
    document.getElementById(errorId).innerHTML = validatePassword(elementId.value);
}

function checkRePassword(formId, errorId) {
    form = document.getElementById(formId);
    document.getElementById(errorId).innerHTML = validateRePassword(form.password.value, form.rePassword.value);
}

function validateUserName(userName) {
    if (userName == "") return "Username field cannot be empty\n";
    if (/[^a-zA-Z0-9]/.test(userName)) return "Username must contain only letters and numbers\n";
    if (!/^[a-zA-Z]/.test(userName)) return "Username must a letter as first character\n";
    if (userName.length < username_min_length) return "Username must contain at least " + username_min_length + " characters\n";
    if (userName.length > username_max_length) return "Username must not contain more than " + username_max_length + " characters\n";
    return "";
}

function validateEmail(email) {
    if (email == "") return "Email address field cannot be empty\n";
    if (!/\S+@\S+\.\S+/.test(email)) return "Invalid email address\n";
    return "";
}

function validatePassword(password) {
    if (password == "") return "Passwords field cannot be empty\n";
    if (!/[a-z]/.test(password)) return "Passwords must contain at least a lowercase letter\n";
    if (!/[A-Z]/.test(password)) return "Passwords must contain at least an uppercase letter\n";
    if (!/[0-9]/.test(password)) return "Passwords must contain at least a number\n";
    if (password.length < password_min_length) return "Passwords must be at least " + password_min_length + " characters\n";
    return "";
}

function validateRePassword(password, rePassword) {
    if (rePassword == "") return "Re-enter passwords field cannot be empty\n";
    if (password != rePassword) return "Passwords are not matched\n";
    return "";
}