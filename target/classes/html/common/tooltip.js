/**
Vertigo Tip by www.vertigo-project.com
Requires jQuery
*/

this.tooltip = function() {    
    this.xOffset = -10; // x distance from mouse
    this.yOffset = 10; // y distance from mouse       
    
    jQuery(".tooltip").unbind().hover(    
        function(e) {
            this.t = this.title;
            this.title = ''; 
            this.top = (e.pageY + yOffset); this.left = (e.pageX + xOffset);
            
            jQuery('body').append( '<p id="tooltip"><img id="tooltipArrow" />' + this.t + '</p>' );
                        
            jQuery('p#tooltip #tooltipArrow').attr("src", '/schoolonline/html/images/tooltip_arrow.png');
            jQuery('p#tooltip').css("top", this.top+"px").css("left", this.left+"px").fadeIn("slow");
            
        },
        function() {
            this.title = this.t;
            jQuery("p#tooltip").fadeOut("slow").remove();
        }
    ).mousemove(
        function(e) {
            this.top = (e.pageY + yOffset);
            this.left = (e.pageX + xOffset);
                         
            jQuery("p#tooltip").css("top", this.top+"px").css("left", this.left+"px");
        }
    );            
    
};

jQuery(document).ready(function(jQuery){tooltip();}) 