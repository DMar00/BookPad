$(document).ready(function(){
    //default
    $("#content").load("./jspComponents/pr-published-stories.jsp");

    //get param from url
    $.urlParam = function(name){
        let results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return results[1] || 0;
    }

    //follow/unfollow btn
    $('#follow').click(function() {
        console.log('follow pressed');
        $.ajax({
            url: "./FollowServlet",
            type: "POST",
            data:{
                username: $.urlParam('username')
            },
            success: function(result) {
                let newFollowers = result.n_followers;
                $('#click-followers>span.num').text(newFollowers);
                let newBtn = result.stat; //1
                if (newBtn==1){//da no follow a si follow
                    $('#follow>i').removeClass('bi-person-plus-fill');
                    $('#follow>i').addClass('bi-person-dash-fill');
                    $('#follow').addClass('unf');   //btn rosso
                    $('#follow>span').text('Unfollow');
                }else{
                    $('#follow>i').removeClass('bi-person-dash-fill');
                    $('#follow>i').addClass('bi-person-plus-fill');
                    $('#follow').removeClass('unf');   //btn viola
                    $('#follow>span').text('Follow');
                }
            },
            error: function(xhr, status, error) {
                console.log("AJAX request failed: " + xhr + " status: " +xhr.status);
                if(xhr.status==312){
                    window.location.replace("./Login");
                }

            }
        });
    });

    /****************************impostazioni*****************************/
    $('#settings').click(function() {
        $("#content").load("./jspComponents/pr-settings.jsp");
    });


    /************avatar**************/
    function getExtension(filename) {
        let parts = filename.split('.');
        return parts[parts.length - 1];
    }

    function isImage(filename) {
        let ext = getExtension(filename);
        switch (ext.toLowerCase()) {
            case 'jpg':
            case 'jpeg':
            case 'bmp':
            case 'png':
                return true;
        }
        return false;
    }

    $('#content').on('click', '#change-avatar', function() {
        $('#menu>div').removeClass("active");
        $(this).addClass("active");
        $('#form-bio').hide();
        $('#form-avatar').show();
        $('#form-email').hide();
        $('#form-pas').hide();
    });

    $('#content').on('click', '#save-avatar', function() {
        let src = document.getElementById("new-avatar");
        if(isImage(src.value)){
            let formData = new FormData();
            formData.append('new_avatar', $('#new-avatar')[0].files[0]);
            formData.append('setting_type', 'avatar');

            $.ajax({
                url: "./SettingsServlet",
                type: "POST",
                data:formData,
                processData: false,
                contentType: false,
                success: function(result) {
                    if(result.updated == true)
                        window.location.reload();
                },
                error: function(xhr, status, error) {
                    console.log("AJAX request failed: " + error);
                }
            });
        }else{
            $("<span style='color: var(--bp-red)'>Formato errato! Formati accettati: jpg, jpeg, png, bmp.</span>").insertAfter("#");
        }
    });

    /***********************bio***************************/
    let len_bio = false;

    $('#content').on( 'keyup', '#new_bio', function() {
        console.log("pressed");
        let bio =$(this).val();
        let len = bio.length;
        $('.count.bio-count>span').text(len);
        if(len>0 && len<=500){
            $(this).css('border','2px solid #B2FFAA');
            $(".count.bio-count>span").css('color','var(--color)');
            len_bio=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $(".count.bio-count>span").css('color','#FA747F');
            len_bio=false;
        }
    });


    $('#content').on('click', '#change-bio', function() {
        $('#menu>div').removeClass("active");
        $(this).addClass("active");
        $('#form-avatar').hide();
        $('#form-bio').show();
        $('#form-email').hide();
        $('#form-pas').hide();
    });

    $('#content').on('click', '#save-bio', function() {
        if(len_bio==true){
            $.ajax({
                url: "./SettingsServlet",
                type: "POST",
                data: {
                    setting_type:"bio",
                    new_bio: $('#new_bio').val()
                },
                success: function(result) {
                    if(result.updated == true)
                        window.location.reload();
                },
                error: function(xhr, status, error) {
                    console.log("AJAX request failed: " + error);
                }
            });
        }
    });

    /***********************credenziali***************************/
    /**email**/
    $('#content').on('click', '#change-email', function() {
        $('#menu>div').removeClass("active");
        $(this).addClass("active");
        $('#form-avatar').hide();
        $('#form-bio').hide();
        $('#form-email').show();
        $('#form-pas').hide();
    });

    $('#content').on('click', '#change-pass', function() {
        $('#menu>div').removeClass("active");
        $(this).addClass("active");
        $('#form-avatar').hide();
        $('#form-bio').hide();
        $('#form-email').hide();
        $('#form-pas').show();
    });

    const mailformat = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    let em = false;
    $('#content').on( 'keyup', '#new_email', function() {
        let email =$(this).val();   //$('#email').val()
        if (email.match(mailformat)){
            $(this).css('border','2px solid #B2FFAA');
            em=true;
            $('.mex-err.em').hide();
        }else{
            $(this).css('border','2px solid #FA747F');
            em=false;
            $('.mex-err.em').show();
        }
    });

    $('#content').on('click', '#save-email', function() {
        if(em==true){
            $.ajax({
                url: "./SettingsServlet",
                type: "POST",
                data: {
                    setting_type:"email",
                    new_email: $('#new_email').val()
                },
                success: function(result) {
                    if(result.updated == true)
                        window.location.reload();
                    else{
                        if(result.existEmail==true) {
                            $("<span style='color: var(--bp-red); font-weight: bold'>Email già esistente!</span>").insertAfter("#save-email");
                        }
                    }
                },
                error: function(xhr, status, error) {
                    console.log("AJAX request failed: " + error);
                }
            });
        }
    });

    /**password**/
    const pswformat=/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/; //almeno una cifra, una lettere minuscola , una maiuscola  - lunghezza minima 8





    /****************************followers*****************************/
    $('#click-followers').click(function() {
        $("#content").load("./jspComponents/pr-followers.jsp");
    });

    /****************************followings*****************************/
    $('#click-followings').click(function() {
        $("#content").load("./jspComponents/pr-followings.jsp");
    });

    /****************************storie scritte*****************************/
    $('#click-pub-stories').click(function() {
        $("#content").load("./jspComponents/pr-published-stories.jsp");
    });

    /****************************storie salvate*****************************/
    $('#click-saved-stories').click(function() {
        $("#content").load("./jspComponents/pr-saved-stories.jsp");
    });
});