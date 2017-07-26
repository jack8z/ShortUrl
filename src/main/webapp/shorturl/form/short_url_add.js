$( document ).ready(function(){
	// 生成短网址
	$("#btn_create").click(function(){
		var url = $.trim($("#url").val());
		var title = $.trim($("#title").val());
		var memo = $.trim($("#memo").val());
		
		if(""==url){
			//alert("请输入长网址");
			//return false;
		}
		
		$("#form1").action = "shorturl.do?m=doCreate";
		$("#form1").submit();
	});
	
	// 重置
	$("#btn_reset").click(function(){
		$("#url").val("");
		$("#title").val("");
		$("#memo").val("");
		return false;
	});
});