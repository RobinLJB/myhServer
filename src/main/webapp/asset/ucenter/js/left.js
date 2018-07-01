$(function(){
	//百叶窗效果
    $('.menu h3').click(function(){
         $(this).next().slideToggle();
    });
	// 为当前点击的导航加上高亮，其余的移除高亮
 	$('#left').find('li').click(function() {
		$(this).addClass('active').siblings('li').removeClass('active');
	});   
    //点击张开菜单
   // showSonMenu($('.menu-son').find('a'));
    
    //鼠标滑过菜单效果
    $('.menu li').hover(function(){
        $(this).addClass('strong');
    },function(){
         $(this).removeClass('strong');
    });

    $('.menu h3').hover(function(){
        $(this).addClass('light');
    },function(){
         $(this).removeClass('light');
    });
});

//获取根据url张开子菜单
function showSonMenu(menulist){
	var url = document.URL;

	menulist.each(function(){
	     if($(this).prop('href') == url){
	    	 $('.menu-son').hide();
	    	 $(this).parents('.menu-son').show();
	     }	
	});
}

function pastpictur(){
	var dir = getDirNum();
	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
	json = encodeURIComponent(json);
	jBox.open("iframe:<c:url value='/uploadFileAction.html?obj='/>"+json,"上传头像",500,220,{buttons: { "关闭":true}});
}