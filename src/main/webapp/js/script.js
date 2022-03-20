$(document).ready(function() {

    $("#btnShow").click(function(){
        $.ajax({
            url : '/process',
            data : {
                action : 'get_translation'
            },
            success : function(response) {
                $('#translation').text(response);
                document.getElementById("btnRight").disabled = false;
                document.getElementById("btnWrong").disabled = false;

                document.getElementById("btnRight").style.backgroundColor ="rgb(61,202,96)";
                document.getElementById("btnWrong").style.backgroundColor ="rgb(251,97,90)";
                document.getElementById("btnRight").style.cursor = "pointer";
                document.getElementById("btnWrong").style.cursor = "pointer";
            }
        });
    });

    $("#btnRight").click(function(){
        $.ajax({
            url : '/process',
            data : {
                action : 'right_next_word'
            },
            success : function(response) {

                let resp = response;
                let word = resp.split(";");

                if (word[0] == "redirect300") {
                    window.location.href = word[1];
                } else {
                    $('#word').text(word[0]);
                    $('#translation').text("?");

                    var countOfGuessedWords = document.getElementById('countOfGuessedWords');
                    var text = countOfGuessedWords.textContent;
                    var number = Number(text);
                    number++;
                    $('#countOfGuessedWords').text(number)

                    document.getElementById("btnRight").disabled = true;
                    document.getElementById("btnWrong").disabled = true;

                    document.getElementById("btnRight").style.backgroundColor ="gray";
                    document.getElementById("btnWrong").style.backgroundColor ="gray";

                    document.getElementById("btnRight").style.cursor = "default";
                    document.getElementById("btnWrong").style.cursor = "default";
                }
            }
        });
    });


    $("#btnWrong").click(function(){
        $.ajax({
            url : '/process',
            data : {
                action : 'wrong_next_word'
            },
            success : function(response) {
                $('#word').text(response);
                $('#translation').text("?");
                document.getElementById("btnRight").disabled = true;
                document.getElementById("btnWrong").disabled = true;

                document.getElementById("btnRight").style.backgroundColor ="gray";
                document.getElementById("btnWrong").style.backgroundColor ="gray";

                document.getElementById("btnRight").style.cursor = "default";
                document.getElementById("btnWrong").style.cursor = "default";
            }
        });
    });


});

function start(){
    $.ajax({
        url : '/process',
        data : {
            action : 'get_first_word'
        },
        success : function(response) {
            $('#word').text(response);
            $('#translation').text("?");
            document.getElementById("btnRight").disabled = true;
            document.getElementById("btnWrong").disabled = true;

            document.getElementById("btnRight").style.backgroundColor ="gray";
            document.getElementById("btnWrong").style.backgroundColor ="gray";
        }
    });
};