$(document).ready(function(){
    function showChapter(numero, idStoria) {
        $.ajax({
            url: "./ChapterServlet",
            type: "POST",
            data:{
                numero: numero,
                id: idStoria
            },
            success: function(result) {
                $('#chap_title').text(result.titolo);
                $('#chapter-content').text(result.content);
            },
            error: function(xhr, status, error) {
                console.log("AJAX request failed: " + error);
            }
        });
    }

    //get id from url
    $.urlParam = function(name){
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return results[1] || 0;
    }
    let paramValue = $.urlParam('id');
    console.log(paramValue);

    //show first chap
    showChapter(1, paramValue);
    $('.chap-found:first').addClass('active');

    //click chapter
    $('#list-chaps').on('click', '.chap-found', function() {
        let num = $(this).attr("id");
        showChapter( num.substring(1), paramValue);
        $('.chap-found').removeClass('active');
        $(this).addClass('active')
    });

    /********************************add/remove likes*********************************/
    $('#click-like').click(function() {
        $.ajax({
            url: "./LikeServlet",
            type: "POST",
            data:{
                id: paramValue
            },
            success: function(result) {
                if(result.status == "true") { //da no like a si like
                    $('#click-like').removeClass("bi-suit-heart");
                    $('#click-like').addClass("bi-suit-heart-fill");
                    $('#n_likes').text(result.num);
                }else{  //da si like a no like
                    $('#click-like').removeClass("bi-suit-heart-fill");
                    $('#click-like').addClass("bi-suit-heart");
                    $('#n_likes').text(result.num);
                }
            },
            error: function(xhr, status, error) {
                console.log("AJAX request failed: " + error);
            }
        });
    });

    /********************************add comments*********************************/
    $('#txt-commment').keyup(function(){
        let txt =$(this).val();
        let len = txt.length;
        $('#count-ch-com>span').text(len);
        if(len>0 && len<=500){
            $("#count-ch-com>span").css('color','#B2FFAA');
        }else{
            $("#count-ch-com>span").css('color','var(--bp-red)');
        }
    });

    $('#add-comment').click(function() {
        let comment = $('#txt-commment').val();
        if(comment.length>0 && comment.length<=500){
            $.ajax({
                url: "./AddCommentServlet",
                type: "POST",
                data:{
                    comment: comment,
                    id_story:paramValue
                },
                success: function(result) {
                    $('#list-comments').append('<div class="comment"><div class="info1"><img src="./Image?type=avatar&username='+result.username+'" alt="avatar"><a href="${pageContext.request.contextPath}/Profile?username='+result.username+'" class="username">'+result.username+'</a></div><span class="cont">'+result.txt+'</span><span class="date">'+result.data+'</span></div>');
                    $('#txt-commment').val('');
                    $('#count-ch-com>span').text('0');
                    $("#count-ch-com>span").css('color','var(--color)');
                    $("#n_com").text(result.num_comm);
                    if(result.num_comm==="1") $('#no_comments').hide();
                },
                error: function(xhr, status, error) {
                    console.log("AJAX request failed: " + error+ "xhr:"+xhr);
                }
            });
        }
    });

    /********************************save/remove story*********************************/
    $('#click-save').click(function() {
        console.log("click");
        $.ajax({
            url: "./SaveStoryServlet",
            type: "POST",
            data:{
                id: paramValue
            },
            success: function(result) {
                if(result.status == "true") { //da no save a si save
                    $('#click-save').removeClass("bi-bookmark");
                    $('#click-save').addClass("bi-bookmark-fill");
                    $('#n_savings').text(result.num);
                }else{  //da si save a no save
                    $('#click-save').removeClass("bi-bookmark-fill");
                    $('#click-save').addClass("bi-bookmark");
                    $('#n_savings').text(result.num);
                }
            },
            error: function(xhr, status, error) {
                console.log("AJAX request failed: " + error);
            }
        });
    });

});