document.getElementById('dataForm').addEventListener('submit', function (e) {
    e.preventDefault();

    var data = {
        title: document.getElementById('title').value,
        text: document.getElementById('text').value
    };

    fetch('/news-mail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (response.status === 201) { // Check if the response status is 201 (Created)
                var locationHeader = response.headers.get('Location');
                if (locationHeader) {
                    window.location.href = locationHeader; // Redirect to the URL specified in the Location header
                } else {
                    console.log('Success: Data saved, but no Location header provided.');
                }
            } else {
                console.error('Error: Unexpected response status:', response.status);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
});
