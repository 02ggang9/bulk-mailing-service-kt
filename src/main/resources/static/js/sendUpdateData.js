document.getElementById('dataForm').addEventListener('submit', function (e) {
    e.preventDefault();

    // Extract mailId from the current URL
    var currentURL = window.location.href;
    var mailId = currentURL.substring(currentURL.lastIndexOf('/') + 1);

    var data = {
        title: document.getElementById('title').value,
        text: document.getElementById('text').value
    };

    var url = '/news-mail/' + mailId; // Construct the URL dynamically

    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (response.status === 201) {
                var locationHeader = response.headers.get('Location');
                if (locationHeader) {
                    window.location.href = locationHeader;
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
