
var userRole;

//セッション情報を取得
function getSessionInfo(){

}

//初期表示
function executeAjax(){
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/booksList',
		dataType : 'json',
		success : function(json){
			console.log(json);

			for(var i=0; i<json.length; i++){

				var row = '<tr>'+
				'<td>'+'<a id="detail" onclick=moveToDetail(\''+json[i].title+'\')  href="">'+json[i].title+'</a>'+'</td>'+
				'<td>'+ json[i].author+'</td>'+
				'<td>'+ json[i].status+'</td>'+
				'<td>'+ '<input type="button" value="借りる" id="borrow">'+'</td>'+ //ロールが図書管理者のとき、編集削除ボタンをつける
				'</tr>';

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

	var requestQuery = {
		title : inputTitle,
		author : inputAuthor,
		genre : inputGenre,
		//frequency : inputFrequency,//図書管理者用のページを表示するときにfrequencyを入れる
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
					'<td>'+ '<input type="button" value="借りる" id="borrow">'+'</td>'+ //ロールが図書管理者のとき、編集削除ボタンをつける
					'</tr>';

					$('#booksTable').append(row);
				}
			}
		}
	});
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

$(document).ready(function(){

	executeAjax();
	pulldownList();

	$('#search').click(booksSearch);
	$('#detail').click(moveToDetail);

});