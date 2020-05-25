function judgAjax() {
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/SessionJudgServlet',
		dataType : 'json',
		async : false,
		success : function(json) {
			console.log(json);
			if(json.result === "no"){
				var result ='<a href="./Login.html">'+"ログインしてください"+'</a>'
				$('#all').html(result )
			}
		}
	});
}
function executeAjax() {
	'use strict';
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/PetitionApprovalListServlet',
		dataType : 'json',
		async : false,
		success : function(json) {
			console.log(json);
			for (var i = 0; i < json.length; i++) {
				var element = json[i];
				var record = '<tr>'
					+ '<td>' + element.title + '</td>'
						+ '<td>' + element.name+ '</td>'
					+ '<td><button id="delet'+ element.title+ '" title="'+ element.title +'" result="rejected">' + "却下" + '</button></td>'
					+ '<td><button id="'+ element.title + '" result="approv" title="'+ element.title +'">' + "承認" + '</button></td>'
						+ '</tr>';
				$('#table_data').append(record)
//				var op = '<option>' + element.bushoName + '</option>'
//				$('#Busho').append(op)

			$("#delet" + element.title).bind('click', deleteAjax);
			$("#" +element.title).bind('click', deleteAjax);

			}

		}
	});

}


function deleteAjax(){
	console.log($(this).attr('title'));
	console.log($(this).attr('result'));
	var requestQuery = {
		title : $(this).attr('title'),
		result : $(this).attr('result')
	};
	'use strict';
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/ApprovalRejectedServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			koshin();
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
			}

	});
}
//ログアウト機能
function logout() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		loginRequest : $(this).attr('value'),
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : '/myFirstApp/LoginLogoutServlet',// url変えろ
		data : requestQuery,
		success : function(json) {
			if (json.result === "ok") {
				alert('ログインして');
				// 画面遷移

			} else {
				alert('ログアウトしました。');
				location.href = 'Login.html';
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

function koshin(){
	location.reload();
}

$(document).ready(function() {
	'use strict';
	judgAjax();
	// 初期表示用
	executeAjax();
	$('#logout').click(logout);

});