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
    console.log(result);
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
				var row = '<tr>'
				if(userRole === "MANAGER"){
					row +='<td>'+'<a id="detail" class="title" onclick=moveToDetail(\''+json[i].title+'\')  href="./ManagerBookDetail.html?title='+json[i].title+'\">'+json[i].title+'</a>'+'</td>'
					}else{
					row +='<td>'+'<a id="detail" class="title" onclick=moveToDetail(\''+json[i].title+'\')  href="./MemberBookDetail.html?title='+json[i].title+'\">'+json[i].title+'</a>'+'</td>'
					};
					row +='<td>'+ json[i].author+'</td>'+
				'<td>'+ json[i].status+'</td>'+
				'<td>'+ '<input type="button" value="借りる" class="rental" id="borrow" onclick="borrowBooks(\''+json[i].bookId+'\')">'+'</td>' //ロールが図書管理者のとき、編集削除ボタンをつける
				if(userRole === "MANAGER"){
					 row +=
					'<td>'+'<input type="button" value="編集" class="edit" id="edit"  onclick="location.href=\'./Add.html?bookId='+json[i].bookId+'\'"  onclick="change(\''+json[i].bookId+'\')">'+'</td>'
					+'<td>'+'<input type="button" value="削除" class="delete" id="delete" onclick="deletion(this,\''+json[i].bookId+'\')">'+'</td>'
				};
				+'</tr>';
				$('#booksTable').append(row);
				$("td:contains('貸出中')").css("color","#ff0000");
			}
		}
	});
}
//書籍の検索
function booksSearch(){
	console.log('click');
	var inputTitle = $('#title').val();
	var inputAuthor = $('#author').val();
	var inputGenre = $('#genre').val();
	if(userRole === "MANAGER"){
		var inputFrequency = $('#frequency').val();
		var requestQuery = {
				title : inputTitle,
				author : inputAuthor,
				genre : inputGenre,
				frequency : inputFrequency//図書管理者用のページを表示するときにfrequencyを入れる
			};
	}else if(userRole === "MEMBER"){
		var requestQuery = {
				title : inputTitle,
				author : inputAuthor,
				genre : inputGenre,
			};
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
			if(json.length === 0){
				var message = '<p>'+'該当する書籍がありません'+'</p>'
				$('#booksTable').append(message);
			}else{
				var columName = '<tr>'+
				'<th>'+'書籍名'+'</th>'+
				'<th>'+'作者'+'</th>'+
				'<th>'+'貸出状況'+'</th>'+
				'<th>'+'レンタル'+'</th>'+
				'<th>'+'書籍編集'+'</th>'+
				'<th>'+'書籍削除'+'</th>'+
				'</tr>'
				$('#booksTable').append(columName);
				for(var i=0; i<json.length; i++){
					var row = '<tr>'
						if(userRole === "MANAGER"){
							row +='<td>'+'<a id="detail" class="title" onclick=moveToDetail(\''+json[i].title+'\')  href="./ManagerBookDetail.html?title='+json[i].title+'\">'+json[i].title+'</a>'+'</td>'
							}else{
							row +='<td>'+'<a id="detail" class="title" onclick=moveToDetail(\''+json[i].title+'\')  href="./MemberBookDetail.html?title='+json[i].title+'\">'+json[i].title+'</a>'+'</td>'
							};
							row +='<td>'+ json[i].author+'</td>'+
						'<td>'+ json[i].status+'</td>'+
						'<td>'+ '<input type="button" value="借りる" class="rental" id="borrow" onclick="borrowBooks(\''+json[i].bookId+'\')">'+'</td>' //ロールが図書管理者のとき、編集削除ボタンをつける
					if(userRole === "MANAGER"){
						row +=
						'<td>'+'<input type="button" value="編集" class="edit" id="syain_edit"  onclick="location.href=\'./Add.html?bookId='+json[i].bookId+'\'"" onclick="edit(\''+json[i].bookId+'\')">'+'</td>'
						+'<td>'+'<input type="button" value="削除" class="delete" id="syain_delete" onclick="deletion(this,\''+json[i].bookId+'\')">'+'</td>'
					};
					+'</tr>';
					$('#booksTable').append(row);
					$("td:contains('貸出中')").css("color","#ff0000");
				}
			}
		}
	});
}
//日付を取得
function getDate(day) {
	  var date = new Date();
	  date.setDate(date.getDate() + day);
	  var year  = date.getFullYear();
	  var month = date.getMonth() + 1;
	  var day   = date.getDate();
	   var today = String(year) + "-" + String(month) + "-" + String(day);
	  return today;
	}
//貸出機能
function borrowBooks(bookId){
	var id = bookId;
	var requestQuery = {
		bookId : id,
		today: getDate(0),
		dueDate : getDate(14)
	};
	console.log(requestQuery);
	$.ajax({
		type:'POST',
		url : '/myFirstApp/BorrowBooksServlet',
		dataType : 'json',
		data : requestQuery,
		success : function(json){
			console.log(json);
			if(json !== '貸出中'){
				alert('書籍を借りました')
				document.location.reload()
			}else{
				alert('貸出中です。')
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
//削除機能（画面から）
var deletion = function(o,bookId){
	console.log('aaa');
	//ディスプレイから表示を消す
	var TR = o.parentNode.parentNode;
	var book=bookId;
	var requestQuery={bookId:bookId};
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myFirstApp/DeleteServlet',
		data : requestQuery,
		success : function(result) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', result);
			// 登録完了のアラート
			if(result==true){
			alert('削除が完了しました');
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
//プルダウンリストにジャンルを表示
function pulldownList(){
	console.log('pulldownList');
	$.ajax({
		type :'GET',
		url : '/myFirstApp/BookGenre',
		dataType : 'json',
		success :function(json){
			console.log(json);
			var blank =  '<option>'+'</option>';
			$('#genre').append(blank);
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
//			if (json.result === "ok") {
//				alert('ログインして');
//				// 画面遷移
//
//			} else {
				alert('ログアウトしました。');
				location.href = 'Login.html';
//		    }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
$(document).ready(function(){
	judgAjax();
	executeAjax();
	pulldownList();
	$('#search').click(booksSearch);
	$('#detail').click(moveToDetail);
	$('#logout').click(logout);
});