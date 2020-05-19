

function executeAjax(){
	$.ajax({
		type : 'GET',
		url : '/myFirstApp/booksList',
		dataType : 'json',
		success : function(json){
			console.log(json);

			for(var i=0; i<json.length; i++){

				var row = '<tr>'+
				'<td>'+num +'</td>'+
				'<td>'+ json[i].title+'</td>'+
				'<td>'+ json[i].author+'</td>'+
				'<td>'+ json[i].status+'</td>'+
				'<td>'+ '<input type="button" value="借りる" id="borrow">'+'</td>'+ //ロールが図書管理者のとき、編集削除ボタンをつける
				'</tr>';

				$('#booksTable').append(row);
			}

		}

	});
}