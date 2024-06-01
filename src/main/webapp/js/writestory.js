$(document).ready(function(){
    /****************************************tags**************************************/
    // STATIC-PARENT on  EVENT  DYNAMIC-CHILD
    $('#tagSection>div').on('click', '.tag', function() { //eliminazione tag
        $(this).remove();
    });

    //aggiunta tag
    $("#tags").keyup(function(e){
        if(e.key == " ") { //e-key singolo carattere tasto
            const txt = $(this).val(); //parola scritta con spazio
            const txt2 = txt.replace(/ /g, ''); //elimina spazi

            //se una volta rimpiazzati gli spazi la lunghezza è > 0 (quindi c'è almeno un carattere')
            if(txt2.length >0) {
                $('#tagSection>div').append('<span class="tag"><i class="bi bi-x-circle-fill"></i>'+txt2+'</span><input type="hidden" name="tag_value" value='+txt2+'>');
                $("#tags").val(''); //svuoto input box
            }
        }
    });

    /****************************************add/remove capitolo**************************************/
    $("button.addChap").click(function(){
        $('.chaps').append('<div class="capitolo"><div class=""><span class="c"><i class="bi bi-filter-left"></i>Capitolo</span><button class="removeChap" type="button"><i class="bi bi-trash3-fill"></i></button></div><label>Titolo*</label><input type="text" name="chap_title" class="chap_title"><span class="count ch_tit"><span>0</span>/60</span><label>Testo*</label><textarea name="chap_text" class="chap_text"></textarea><span class="count ch_txt"><span>0</span>/60000</span></div>');
    });

    $('.chaps').on('click', 'button.removeChap', function() {
        $(this).closest('.capitolo').remove();
    });

    /****************************************input controls**************************************/
    let t=false, tr=false;

    $('#titolo').keyup(function(){
        let titolo =$(this).val();
        let len = titolo.length;
        $('.count.tit>span').text(len);
        if(len>0 && len<=60){
            $(this).css('border','2px solid #B2FFAA');
            $(".count.tit>span").css('color','var(--color)');
            t=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $(".count.tit>span").css('color','#FA747F');
            t=false;
        }
    });

    $('#trama').keyup(function(){
        let trama =$(this).val();
        let len = trama.length;
        $('.count.tr>span').text(len);
        if(len>0 && len<=30000){
            $(this).css('border','2px solid #B2FFAA');
            $(".count.tr>span").css('color','var(--color)');
            tr=true;
        }else{
            $(this).css('border','2px solid #FA747F');
            $(".count.tr>span").css('color','#FA747F');
            tr=false;
        }
    });

    $('.chaps').on('keyup', '.chap_title', function() {
        let ch_titolo =$(this).val();
        let len = ch_titolo.length;
        $(this).next().find('span').text(len);
        if(len>0 && len<=60){
            $(this).css('border','2px solid #B2FFAA');
            $(this).next().find('span').css('color','var(--color)');
        }else{
            $(this).css('border','2px solid #FA747F');
            $(this).next().find('span').css('color','#FA747F');
        }
    });

    $('.chaps').on('keyup', '.chap_text', function() {
        let ch_txt =$(this).val();
        let len = ch_txt.length;
        $(this).next().find('span').text(len);
        if(len>0 && len<=60000){
            $(this).css('border','2px solid #B2FFAA');
            $(this).next().find('span').css('color','var(--color)');
        }else{
            $(this).css('border','2px solid #FA747F');
            $(this).next().find('span').css('color','#FA747F');
        }
    });

    /****************************************img**************************************/
    let m = true;
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

    function showImage(src,target) {
        let fr = new FileReader();
        fr.onload = function(e) {
            if (isImage(src.value)){
                target.src = this.result;   // when image is loaded, set the src of the image where you want to display it
            }
            else target.src = "./assets/img/no-image-cover.png";    //if image has incorrect format show default cover image
        };
        src.addEventListener("change",function() {
            if(!(typeof src.files[0] === "undefined")){    //if is not empty src
                fr.readAsDataURL(src.files[0]); // fill fr with image data
                if (!isImage(src.value)){
                    m = false;
                    $(".mex-err.img-err").show();
                }else{
                    m=true;
                    $(".mex-err.img-err").hide();
                }
            }else{
                m=true;
                $(".mex-err.img-err").hide();
                target.src = "./assets/img/no-image-cover.png"; // if no image, default cover
            }
        });
    }
    let src = document.getElementById("cover");   //input type file
    let target = document.getElementById("cover-preview"); //img to diplay
    showImage(src,target);


    /****************************************submit checking**************************************/
    $('#pb_story').click(function() {
        /**checking chapters**/
        let ch_titles = document.querySelectorAll(".chap_title");
        let ch_texts = document.querySelectorAll(".chap_text");
        let ct = [], cc = [];
        for (let i = 0; i < ch_titles.length; i++) {
            let x = ch_titles[i].value.length;
            let y = ch_texts[i].value.length;
            if(x>0 && x<=60) ct.push(true);
            else ct.push(false);
            if(y>0 && y<=60000) cc.push(true);
            else cc.push(false);
        }
        let ct_f = ct.every( e  => e == true);
        let cc_f = cc.every( e  => e == true);
        //console.log("titolo = "+t+" - trama = "+tr+" - tit_ch = "+ct_f+" - txt_ch = "+cc_f+" - img = "+m);
        return (t&&tr&&ct_f&&cc_f&&m);
    });


});