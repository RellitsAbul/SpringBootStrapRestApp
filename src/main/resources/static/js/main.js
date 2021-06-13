var password = document.getElementById("passwordNew")
    , confirm_password = document.getElementById("passwordConfirmNew")
    , username = document.getElementById("userNameNew");
var usernames = document.getElementsByName("tdusername");

function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Пароль не совпадает");
    } else {
        confirm_password.setCustomValidity('');
    }
}

function validateUsername() {
    for (var i = 0; i < usernames.length; i++) {
        console.log(usernames[i].innerHTML);
        if (username.value == usernames[i].innerHTML) {

            username.setCustomValidity("Данное имя занято");
            break;
        } else {
            username.setCustomValidity('');
        }
    }
}


$(document).ready(function () {
    restartAllUser();
    $('.BtnNewUse').on('click', function (event) {
        if (confirm_password.checkValidity() && username.checkValidity()) {
            let user = {
                name: $("#firstNameNew").val(),
                lastName: $("#lastNameNew").val(),
                email: $("#emailNew").val(),
                username: $("#userNameNew").val(),
                password: $("#passwordNew").val(),
                roles: getRole("#selectRole")

            }
            console.log(user);
            fetch("api/newUser", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                },
                body: JSON.stringify(user)
            }).then(() => openTabById('nav-home'))
                .then(() => restartAllUser());
            $('input').val('');
        } else {
            document.getElementById("validPass").innerHTML = confirm_password.validationMessage;
            document.getElementById("validUseN").innerHTML = username.validationMessage;
        }
    });
});

function createTableRow(u) {
    let userRole = "";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].role;
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.email}</td>
            <td name="tdusername">${u.username}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/api/${u.id}" type="button" class="btn btn-primary eBtn" data-bs-toggle="modal" data-bs-target="#edit">Edit</a>
            </td>
            <td>
            <a  href="/api/delete/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push({id: $(this).val(), role: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}

function restartAllUser() {
    let UserTableBody = $("#user_table_body")

    UserTableBody.children().remove();

    fetch("/api/allusers")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }


    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #firstNameEd").val(user.name);
            $(".editUser #lastNameEd").val(user.lastName);
            $(".editUser #emailEd").val(user.email);
            $(".editUser #selectRoleEd").val(user.roles);
        });
    }
    if ($(event.target).hasClass('editButton')) {
        let user = {
            id: $('#id').val(),
            name: $('#firstNameEd').val(),
            lastName: $('#lastNameEd').val(),
            email: $('#emailEd').val(),
            roles: getRole("#selectRoleEd")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('userInfo')) {
        userTable();
    }

});

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => restartAllUser());
}

function editModalButton(user) {
    fetch("api/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #edit').modal('hide');
        restartAllUser();
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function logout() {
    document.location.replace("/logout");
}

function userTable() {
    document.location.replace("/user");
}

password.onkeyup = validatePassword;
confirm_password.onkeyup = validatePassword;
username.onkeyup = validateUsername;