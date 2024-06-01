/**************************LIGHT/DARK MODE***************************/
let currentTheme = localStorage.getItem("theme");

if (!currentTheme) {
    localStorage.setItem("theme", "light");
    currentTheme="light";
}

if(currentTheme==="dark"){
    document.documentElement.setAttribute("data-theme", "dark");
}else if(currentTheme==="light"){
    document.documentElement.setAttribute("data-theme", "light");
}

//bootstrap icons
const light_icon = "bi-sun-fill";
const dark_icon = "bi-moon-stars-fill";

$('document').ready(function(){
    if(currentTheme==="dark"){
        $(".switch-mode-icon i").removeClass(dark_icon).addClass(light_icon);
    }else if(currentTheme==="light"){
        $(".switch-mode-icon i").removeClass(light_icon).addClass(dark_icon);
    }

    $('.switch-mode-icon').click(function() {
        if(currentTheme==="light"){
            $(".switch-mode-icon i").removeClass(dark_icon).addClass(light_icon);
            document.documentElement.setAttribute("data-theme", "dark");
            localStorage.setItem("theme", "dark");
        }else if(currentTheme==="dark"){
            $(".switch-mode-icon i").removeClass(light_icon).addClass(dark_icon);
            document.documentElement.setAttribute("data-theme", "light");
            localStorage.setItem("theme", "light");
        }
        currentTheme=localStorage.getItem("theme");
    });
});