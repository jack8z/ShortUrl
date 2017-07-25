$( document ).ready(function(){
	// 生成短网址
	$("#btn_create").click(function(){
		var url = $.trim($("#url").val());
		var title = $.trim($("#title").val());
		var memo = $.trim($("#memo").val());
		
		if(""==url){
			alert("请输入长网址");
			return;
		}
		
		$("#form1").action = "shorturl.do?m=doCreate";
	});
});