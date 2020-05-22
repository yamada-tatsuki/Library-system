
function GetQueryTitle() {
    var result = new Object();
    if (1 < document.location.search.length) {
        // 最初の1文字 (?記号) を除いた文字列を取得する
        console.log(document.location.search);
    	var query = document.location.search.substring(1);
        console.log(query);
        // クエリの区切り記号 (&) で文字列を配列に分割する
        var parameters = query.split('&');
        console.log(parameters);
        for (var i = 0; i < parameters.length; i++) {
            // パラメータ名とパラメータ値に分割する
            var element = parameters[i].split('=');
            console.log(element);
            var paramName = decodeURIComponent(element[0]);
            console.log(paramName);
            var paramValue = decodeURIComponent(element[1]);
            console.log(paramValue);
            // パラメータ名をキーとして連想配列に追加する
            result[paramName] = paramValue;
        }
        //result = {id: "EMP0001", name: "tanaka",age:"10"}
    }
    return result;
}

var paramn = GetQueryTitle();
var titlename = paramn["title"];

function executeAjax () {
	'use strict';
	var requestQuery = { title : titlename} ;
	console.dir(requestQuery);
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/api/managerbookdetail',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {

			console.log(titlename);
			for (var i = 0; i < json.length; i++) {


				var elements = json[i];
				var s = elements.boughtOn;
				var str = s.replace( /-/g, '/' );
				var ymd =  str.substr(0, 10);

				console.log(ymd);

				$('#js-title').html(elements.title);
				$('#js-author').html(elements.author);
				$('#js-publisher').html(elements.publisher);
				$('#js-genre').html(elements.genre);
				$('#js-status').html(elements.status);
				$('#js-boughtby').html(elements.boughtBy);
				$('#js-boughton').html(ymd);
				$('#js-renddata').html(elements.rendData);

			}
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

$(document).ready(function () {
	'use strict';

	// 初期表示用
	executeAjax();
	$('#logout').click(logout);

	//$('#table_data').ready('road',executeAjax);

});