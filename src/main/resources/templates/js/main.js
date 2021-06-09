var password = document.getElementById("exampleInputPassword1")
    , confirm_password = document.getElementById("exampleInputPasswordConfirm")
    , username = document.getElementById("exampleInputUser1");
var usernames = document.getElementsByName("usernames");

function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}

function validateUsername() {
    for (var i = 0; i < usernames.length; i++) {
        if (username.value == usernames[i].innerHTML) {
            username.setCustomValidity("Данное имя занято");
            break;
        } else {
            username.setCustomValidity('');
        }
    }
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
        confirm_password.setCustomValidity('');
    }
}

username.onkeyup = validateUsername;
password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;