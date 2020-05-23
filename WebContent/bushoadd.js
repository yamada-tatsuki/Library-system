// AjaxでJSONを取得する
/*function executeAjax () {
	'use strict';

	// ?以降のパラメータを取得
	// 今回で言うとhttp://localhost:8080/wt1/hobby.html?q=0001でいう0001が取得される
	var parameter  = location.search.substring( 1, location.search.length );
	parameter = decodeURIComponent( parameter );
	parameter = parameter.split('=')[1];

	// --------------- TODO 編集ここから---------------
	var requestQuery = { syainid  : parameter};*/

var addbusho = function(){
	var inputBushoId = $('#js-add-bushoid').val();
	var inputBushoName = $('#js-add-bushoname').val();

	var requestQuery = {
			bushoId : inputBushoId,
			NewbushoName : inputBushoName
	};
	console.log(requestQuery);

	$.ajax({
		type : 'POST',
		url : '/Syain/api/addbushokanri',
		dataType : 'json',
		data :requestQuery,
		success : function (json) {

					// サーバーと䛾通信に成功した時䛾処理
					// 確認䛾ために返却値を出力
					console.log(json);
					// 登録完了のアラート
					alert('登録が完了しました');
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) { //successとセット
					// サーバーと䛾通信に失敗した時䛾処理
					//alert('データ䛾通信に失敗しました');
					console.log(errorThrown)
					}
					});
}

$(document).ready(function () {
	//'use strict';
	$('#js-settei-button').click(addbusho);
	// 初期表示用
	//executeAjax();
	// 更新ボタンにイベント設定
//	$('#searchBtn').bind('click',executeAjax);

});