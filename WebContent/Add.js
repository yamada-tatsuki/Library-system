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

/* 商品情報を登録するファンクション */
var registItem = function () {
	var inputItemTitle=$('#title').val();
	var inputItemAuthor=$('#author').val();
	var inputItemPublisher=$('#publisher').val();
	var inputItemGenre=$('#janru').val();
	var inputItemBoughtOn=$('#bought_on').val();
	var inputItemBoughtBy=$('#bought_by').val();
	var requestQuery = {
			itemTitle:inputItemTitle,
			itemAuthor:inputItemAuthor,
			itemPublisher:inputItemPublisher,
			itemGenre:inputItemGenre,
			itemBoughtOn:inputItemBoughtOn,
			itemBoughtBy:inputItemBoughtBy
	};
	console.log('requestQuery',requestQuery);
	// サーバーにデータを送信する。
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myFirstApp/AddServlet',
		data : requestQuery,
		success : function(json) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', json);
			// 登録完了のアラート
			alert('登録が完了しました');
			location.reload();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			// サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}
//編集機能
var change = function(){
	console.log('aaa');
	//ディスプレイから表示を消す
	var inputItemTitle=$("#title").val();
	var inputItemAuthor=$('#author').val();
	var inputItemPublisher=$('#publisher').val();
	var inputItemGenre=$('#janru').val();
	var inputItemBoughtOn=$('#bought_on').val();
	var inputItemBoughtBy=$('#bought_by').val();
	var bookId=getParam('bookId');
	var requestQuery = {
			itemTitle:inputItemTitle,
			itemAuthor:inputItemAuthor,
			itemPublisher:inputItemPublisher,
			itemGenre:inputItemGenre,
			itemBoughtOn:inputItemBoughtOn,
			itemBoughtBy:inputItemBoughtBy,
			bookId:bookId
	};
	$.ajax({
		type : 'GET',
		dataType:'json',
		url : '/myFirstApp/ChangeServlet',
		data : requestQuery,
		success : function(result) {
			// サーバーとの通信に成功した時の処理
			// 確認のために返却値を出力
			console.log('返却値', result);
			// 登録完了のアラート
			if(result==true){
			alert('変更が完了しました');
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
function getParam(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
function GetParameter(){
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];
	if(!parameter){
	$('#add-button').html('<button id="register-button" class="regist" onclick="registItem()">登録</button>')
		console.log('ない')
	}else{
	$('#add-button').html('<button id="change-button" class="edit" onclick="change()">変更</button>')
	$('#add-button2').html('<button id="return-button" class="return" onclick="location.href=\'./ManagerBooksList.html?role=MANAGER\'">キャンセル</button>')
		console.log('ある')
	}
}
/**
 * 読み込み時の動作
 */
$(document).ready(function() {
	judgAjax();
	// 登録ボタンを押したときのイベント
	//$('#js-register').click(registItem);
	GetParameter();
});