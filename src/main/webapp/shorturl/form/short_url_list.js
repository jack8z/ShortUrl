$( document ).ready(function(){
	// 上一页
	$("#btn_go_previous").click(function(){
		var page_count = $("#page_count").val() | 0;
		var page_size = $("#page_size").val() | 0;
		var curr_page = $("#curr_page").val() | 0;
		if(curr_page>1){
			$("#curr_page").val(curr_page-1);
			
			$("#form1").action = "shorturl.do?m=listPage";
			$("#form1").submit();
			return false;
		}
	});
	// 下一页
	$("#btn_go_next").click(function(){
		var page_count = $("#page_count").val() | 0;
		var page_size = $("#page_size").val() | 0;
		var curr_page = $("#curr_page").val() | 0;
		if(curr_page<page_count){
			$("#curr_page").val(curr_page+1);
			
			$("#form1").action = "shorturl.do?m=listPage";
			$("#form1").submit();
			return false;
		}
	});
});
