
var userRole;

//URLからパラメータの部分を取得
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

//ロール情報をURLパラメータから取得
var param = GetQueryString();
userRole = param["role"];


//初期表示
function executeAjax(){
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/booksList',
		dataType : 'json',
		success : function(json){
			console.log(userRole);
			console.log(json);

			for(var i=0; i<json.length; i++){

				var row = '<tr>'+
				'<td>'+'<a id="detail" onclick=moveToDetail(\''+json[i].title+'\')  href="">'+json[i].title+'</a>'+'</td>'+
				'<td>'+ json[i].author+'</td>'+
				'<td>'+ json[i].status+'</td>'+
				'<td>'+ '<input type="button" value="借りる" id="borrow">'+'</td>' //ロールが図書管理者のとき、編集削除ボタンをつける
				if(userRole === "MANAGER"){
					 row +=
					'<td>'+'<input type="button" value="編集" id="edit" onclick="edit(\''+json[i].bookId+'\')">'+'</td>'
					+'<td>'+'<input type="button" value="削除" id="delete" onclick="deletion(this,\''+json[i].bookId+'\')">'+'</td>'
				};
				+'</tr>';

				$('#booksTable').append(row);
			}

		}
	});
}
// json[i].title　←書籍名にハイパーリンクつける方法がうまく行かなったとき用に避難

//書籍の検索
function booksSearch(){
	console.log('click');
	var inputTitle = $('#title').val();
	var inputAuthor = $('#author').val();
	var inputGenre = $('#genre').val();
	var inputFrequency = $('#frequency').val();

	if(userRole === "MANAGER"){
		var requestQuery = {
				title : inputTitle,
				author : inputAuthor,
				genre : inputGenre,
				frequency : inputFrequency//図書管理者用のページを表示するときにfrequencyを入れる
			};
	}else{
		var requestQuery = {
				title : inputTitle,
				author : inputAuthor,
				genre : inputGenre,
			}
	}

	console.log(requestQuery);
	$.ajax({
		type: 'GET',
		url: '/myFirstApp/BooksSearchServlet',
		dataType : 'json',
		data: requestQuery,

		success : function(json){
			console.log(json);
			reset();
			if(json.length == 0){
				var message = '<p>'+'該当する書籍がありません'+'</p>'
				$('#booksTable').append(message);
			}else{
				var columName = '<tr>'+
				'<th>'+'書籍名'+'</th>'+
				'<th>'+'作者'+'</th>'+
				'<th>'+'貸出状況'+'</th>'+
				'<th>'+'レンタル'+'</th>'+
				'</tr>'
				$('#booksTable').append(columName);

				for(var i=0; i<json.length; i++){
					var row = '<tr>'+
					'<td>'+ '<a id="detail" onclick=moveToDetail(\''+json[i].title+'\')  href="">'+json[i].title+'</a>'+'</td>'+
					'<td>'+ json[i].author+'</td>'+
					'<td>'+ json[i].status+'</td>'+
					'<td>'+ '<input type="button" value="借りる" id="borrow">'+'</td>'//ロールが図書管理者のとき、編集削除ボタンをつける
					if(userRole === "MANAGER"){
						row +=
						'<td>'+'<input type="button" value="編集" id="syain_edit" onclick="edit(\''+json[i].bookId+'\')">'+'</td>'
						+'<td>'+'<input type="button" value="削除" id="syain_delete" onclick="deletion(this,\''+json[i].bookId+'\')">'+'</td>'
					};
					+'</tr>';

					+'</tr>';

					$('#booksTable').append(row);
				}
			}
		}
	});
}
//貸出機能
function borrowBooks(title){

	$.afax({
		type : 'GET',
		url : 'myFirstApp/BorrowBooksServlet',
		dataType : 'json',
		data : request
	})
}
//削除機能（画面から）
var deletion = function(o,syainId){
	console.log('aaa');
	//ディスプレイから表示を消す
	var TR = o.parentNode.parentNode;
}

//プルダウンリストにジャンルを表示
function pulldownList(){
	console.log('pulldownList');
	$.ajax({
		type :'GET',
		url : '/myFirstApp/booksList',
		dataType : 'json',
		success :function(json){
			console.log(json);
			for(var i=0; i<json.length; i++){
				var genre = '<option>' + json[i].genre + '</option>';
				$('#genre').append(genre);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert('プルダウンリストの表示に失敗');
			console.log(errorThrown);
		}
	});
}

//詳細ページに遷移
function moveToDetail(title){
	console.log('click');
	var bookTitle = title;
	var requestQuery = {
			title : bookTitle
	};
}

//初期表示・検索結果の表を消去
var reset = function(){
	console.log('reset');
	$('#booksTable').empty();
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

$(document).ready(function(){
	executeAjax();
	pulldownList();

	$('#search').click(booksSearch);
	$('#detail').click(moveToDetail);
	$('#logout').click(logout);

});