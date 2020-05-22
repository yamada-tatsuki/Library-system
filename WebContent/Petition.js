function executeAjax() {
	var emp = '0001';
	var requestQuery = { employeeid : emp} ;
    console.log(emp);
	console.dir(requestQuery);
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/api/petitionList',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {

			for (var i = 0; i < json.length; i++) {

				var element = json[i];

				var record = '<tr>'
					+ '<td>' + element.title + '</td>'
					+ '<td>' + element.author + '</td>'
					+ '<td>' + element.status + '</td>'
					+ '</tr>';

				$('#PetitionTable').append(record)
			}
		}
	});
}

var add = function(){
	var inputtitle = $('#js-title').val();
	var inputauthor = $('#js-author').val();

	var requestQuery = {
			Title : inputtitle,
			Author : inputauthor
	};
	console.log(requestQuery);

	$.ajax({
		type : 'POST',
		url : '/myFirstApp/api/petition',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {

					// サーバーと䛾通信に成功した時䛾処理
					// 確認䛾ために返却値を出力
					console.log(json);
					// 登録完了のアラート
					alert('書籍の嘆願が完了しました');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) { //successとセット
					// サーバーと䛾通信に失敗した時䛾処理
					alert('データの通信に失敗しました');
					console.log(errorThrown)
					}
					});
}

function func1() {
    document.location.reload();
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

$(document).ready(function () {
	//'use strict';
	$('#petition').click(add);
	// 初期表示用
	executeAjax();
	$('#logout').click(logout);

});