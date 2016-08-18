/**
Vertigo Tip by www.vertigo-project.com
Requires jQuery
*/
this.tooltip=function(){this.xOffset=-10;this.yOffset=10;jQuery(".tooltip").unbind().hover(function(a){this.t=this.title;this.title="";this.top=(a.pageY+yOffset);this.left=(a.pageX+xOffset);jQuery("body").append('<p id="tooltip"><img id="tooltipArrow" />'+this.t+"</p>");jQuery("p#tooltip #tooltipArrow").attr("src","/schoolonline/html/images/tooltip_arrow.png");jQuery("p#tooltip").css("top",this.top+"px").css("left",this.left+"px").fadeIn("slow")},function(){this.title=this.t;jQuery("p#tooltip").fadeOut("slow").remove()}).mousemove(function(a){this.top=(a.pageY+yOffset);this.left=(a.pageX+xOffset);jQuery("p#tooltip").css("top",this.top+"px").css("left",this.left+"px")})};jQuery(document).ready(function(a){tooltip()});