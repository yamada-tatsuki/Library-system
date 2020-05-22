<<<<<<< HEAD

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
=======
// AjaxでJSONを取得する
function executeAjax () {
	'use strict';

<<<<<<< HEAD
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];

	//var param = GetQueryString();
	//var title = param["title"];

	//var requestQuery = { title : title} ;
	var requestQuery = { q : parameter} ;
	console.dir(requestQuery);
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/api/managerbookdetail',
=======
	//var parameter  = location.search.substring( 1, location.search.length );
	//parameter = decodeURIComponent( parameter );
	//parameter = parameter.split('=')[1];

	var param = GetQueryString();
	var title = param["title"];

	var requestQuery = { title : title} ;
	console.dir(requestQuery);
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/api/bookdetail',
>>>>>>> d9ee5e2c6515f703fd340ce3679fb32255c860fa
>>>>>>> 8546ff84b694b77a1d23fd5c628249b93e682f0c
		dataType : 'json',
		data :requestQuery,
		success : function (json) {

<<<<<<< HEAD
			console.log(titlename);

=======
<<<<<<< HEAD
			//for (var i = 0; i < json.length; i++) {


				var elements = json[1];
				var str = elements.boughtOn;
				var boughton = str.replace( /-/g, '/' );

				console.log(boughton);
=======
>>>>>>> 8546ff84b694b77a1d23fd5c628249b93e682f0c
			for (var i = 0; i < json.length; i++) {


				var elements = json[i];
<<<<<<< HEAD
				var s = elements.boughtOn;
				var str = s.replace( /-/g, '/' );
				var ymd =  str.substr(0, 10);

				console.log(ymd);
=======
>>>>>>> d9ee5e2c6515f703fd340ce3679fb32255c860fa
>>>>>>> 8546ff84b694b77a1d23fd5c628249b93e682f0c

				$('#js-title').html(elements.title);
				$('#js-author').html(elements.author);
				$('#js-publisher').html(elements.publisher);
				$('#js-genre').html(elements.genre);
<<<<<<< HEAD
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

=======
				$('#js-status').html(elements.status);
<<<<<<< HEAD
				$('#js-boughtby').html(elements.boughtBy);
				$('#js-boughton').html(boughton);
				$('#js-renddata').html(elements.rendData);

			}
		//}
=======

				//var record = '<tr>'
				//	+ '<td>' + element.title + '</td>'
					//+ '<td>' + element.author + '</td>'
					//+ '<td>' + element.publisher + '</td>'
					//+ '<td>' + element.genre + '</td>'
					//+ '<td>' + element.status + '</td>'
					//+ '</tr>';

				//$('#table_data').append(record)
			}
		}
>>>>>>> d9ee5e2c6515f703fd340ce3679fb32255c860fa
	});
}

function GetQueryString() {
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

>>>>>>> 8546ff84b694b77a1d23fd5c628249b93e682f0c
$(document).ready(function () {
	'use strict';

	// 初期表示用
	executeAjax();
<<<<<<< HEAD
	$('#logout').click(logout);

	//$('#table_data').ready('road',executeAjax);
=======

<<<<<<< HEAD
	//$('#table_data').ready('road',executeAjax);
=======
	$('#table_data').ready('road',executeAjax);
>>>>>>> d9ee5e2c6515f703fd340ce3679fb32255c860fa
>>>>>>> 8546ff84b694b77a1d23fd5c628249b93e682f0c

});