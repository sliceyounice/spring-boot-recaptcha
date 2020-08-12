$(document).ready(function() {
    $('button.btn-primary').on('click', function(event) {
        event.preventDefault();
        let response = grecaptcha.getResponse();
        if (response.length == 0) {
            alert("reCaptcha didn`t pass");
        }
        else if ($('#textInput').val().length == 0) {
            alert("Please fill an input field");
        }
        else {
            $.ajax({
                type: "POST",
                url: "/?g-recaptcha-response=" + response,
                data: JSON.stringify({text: $('#textInput').val()}),
                contentType: 'application/json',
                success: function (response) {
                    alert('You sent: ' + response);
                    $('#textInput').val('');
                    grecaptcha.reset();
                },
                error: function (response) {
                    alert('Unable to validate reCaptcha')
                    grecaptcha.reset()
                }
            });
        }
    });
});