<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/html" id="previewTpl">
    <div class="slider-for">
        <!for(var i=0;i < images.length ;i++){
            var image=images[i];
        !>
        <div>
            <img data-lazy="<!=image.path!>" selfId="<!=image.id!>" alt="<!=image.relationType!>"/>
        </div>
        <!}!>
    </div>
    <div class="slider-nav">
        <!for(var i=0;i < images.length ;i++){
           var image=images[i];
        !>
            <div>
                <img data-lazy="<!=image.path!>" selfId="<!=image.id!>" alt="<!=image.relationType!>">
            </div>
        <!}!>
    </div>
</script>
