$('document').ready(function(){
    const mailformat = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
    const userformat =  /^(?=.*[a-z])(?!.*(_)\1)([a-z0-9_]+){4,15}$/; //almeno una lettera , no _ consecutivi , ammessi az09_ , lunghezza compresa tra 4 e 15
    const pswformat=/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/; //almeno una cifra, una lettere minuscola , una maiuscola  - lunghezza minima 8

    let pswd;
    let e=false, u=false, p=false, p2=false;

    $('#email').keyup(function(){
        let email =$(this).val();   //$('#email').val()
        if (email.match(mailformat)){
            $(this).css('border','2px solid #B2FFAA');
            $('.mex-err.em').hide();
            e=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $('.mex-err.em').show();
            e=false;
        }
    });

    $('#username').keyup(function(){
        let username =$(this).val();
        if (username.match(userformat)){
            $(this).css('border','2px solid #B2FFAA');
            $('.mex-err.us').hide();
            u=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $('.mex-err.us').show();
            u=false;
        }
    });

    $('#paswd').keyup(function(){
        pswd =$(this).val();
        if (pswd.match(pswformat)){
            $(this).css('border','2px solid #B2FFAA');
            $('.mex-err.pa').hide();
            p=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $('.mex-err.pa').show();
            p=false;
        }
    });

    $('#paswd2').keyup(function(){
        let pswd2 =$(this).val();
        if(pswd2.match(pswformat) && pswd2===pswd){
            $(this).css('border','2px solid #B2FFAA');
            p2=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            p2=false;
        }

        if(pswd2!==pswd) $('.mex-err.pa2').show();
        else $('.mex-err.pa2').hide();
    });

    $('#sub').click(function(){
        /*console.log("e="+e+" u="+u+" p="+p+" p2="+p2);*/
        return(e && u && p && p2);
    });

})