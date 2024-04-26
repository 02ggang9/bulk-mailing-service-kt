document.addEventListener('DOMContentLoaded', function () {
    var memberForm = document.getElementById('memberForm');
    var nicknameInput = document.getElementById('nickname');
    var emailInput = document.getElementById('email');

    memberForm.addEventListener('submit', function (e) {
        e.preventDefault();
        var memberData = {
            nickname: nicknameInput.value,
            email: emailInput.value
        };

        fetch('/member', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(memberData)
        })
            .then(response => {
                if (!response.create) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => console.log(data))
            .catch(error => console.error('Error:', error));
    });
});
