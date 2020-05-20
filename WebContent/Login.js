/* ログインファンクション */
function login() {
	// 入力されたユーザーIDとパスワード
	var requestQuery = {
		empId : $('#js-login-id').val(),
		password : $('#js-login-pass').val()
	};
	// サーバーからデータを取得する
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : '/myFirstApp/LoginLogoutServlet',// url変えろ
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理

			if (json.result === "ok") {
				alert('成功');
				// 画面遷移
				//location.href = './Shainlist.html';
			} else {
				alert('社員IDかパスワードが間違っています');
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

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
				//location.href = '';
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

/**
 * 読み込み時の動作
 */
$(document).ready(function() {

	// ログインボタンを押したときのイベント
	$('#js-login-button').click(login);
	$('#js-logout-button').click(logout);

});