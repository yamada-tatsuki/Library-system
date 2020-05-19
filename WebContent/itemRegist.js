/*商品情報を登録するファンクション*/
var registItem = function(){
	//入力された商品コード
	var inputItemCd = $('#js-input-code').val();
	var inputItemName = $('#js-input-name').val();
	var inputItemNameKana = $('#js-input-kana').val();
	var inputSalesPrice = $('#js-input-price').val();
	var inputStock = $('#js-input-stock').val();
	var inputUrl = $('#js-input-url').val();
	var inputDescription = $('#js-input-description').val();
	var requestQuery = {
		itemCd:inputItemCd,
		itemName:inputItemName,
		itemNameKana:inputItemNameKana,
		salesPrice:inputSalesPrice,
		stock:inputStock,
		imageUrl:inputUrl,
		description:inputDescription
	};
	console.log('requestQuery',requestQuery);


	//サーバーからデータを取得する
	$.ajax({
		type :'POST',
		url : '/myFirstApp/ItemRegistServlet',
		dataType:'json',
		data:requestQuery,
		success:function(json){
			//サーバーとの通信に成功した時の処理
			//確認のために返却値を出力
			console.log('返却値',json);
			alert('登録が完了しました');
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
			//サーバーとの通信に失敗した時の処理
			alert('データの通信に失敗しました');
			console.log(errorThrown)
		}
	});
}

/**
 * 読み込み時の動作
 */
$(document).ready(function(){
	//登録ボタンを押したときのイベント
	$('#js-regist-button').click(registItem);
});