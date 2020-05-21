function executeAjax () {
	'use strict';


	$.ajax({
		type : 'GET',
		url : '/myFirstApp/MypageServlet',
		dataType : 'json',
		async: false,
		success : function (json) {
			console.log(json);

			for(var i=0;i<json.length;i++){
				var rental = json[i];
				var tableElement='';
				var s = rental.deadline;
				var str = s.replace( /-/g, '/' );
				var ymd =  str.substr(0, 10);
				 tableElement+='<tr>'
								+'<td>'+rental.title+'</td>'
								+'<td>'+rental.author+'</td>'
								+'<td>'+rental.genre+'</td>'
								+'<td>'+rental.publisher+'</td>'
								+'<td>'+ymd+'</td>'
								+'<td>'+'<button class="js-return" id='+rental.title+'>返却</button>'+'</td>'
								+'</tr>'

				$('#syainData').append(tableElement);
			}
			}

	});
	// ---------------ここまで---------------

}
$(document).ready(function () {
	'use strict';
	// 初期表示用
	 executeAjax();
	 $('.js-return').click((e)=>DeleteItem($(e.currentTarget).attr('id')));

});
function DeleteItem (itemTitle){
	var requestQuery={itemTitle:itemTitle};


	// サーバーにデータを送信する。
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myFirstApp/ReturnServlet',
		data : requestQuery,
		success : function(result) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', result);
			// 登録完了のアラート
			if(result==true){
			alert('返却が完了しました');
			location.reload();
			}
			else if(result==false){
				alert('NG');
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}