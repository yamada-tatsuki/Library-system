function executeAjax () {
	'use strict';


	$.ajax({
		type : 'GET',
		url : '/myFirstApp/MypageServlet',
		dataType : 'json',
		async: false,
		success : function (json) {
			console.log(json);

			for(var i=0;i<json.length;i++){
				var rental = json[i];
				var tableElement='';
				 tableElement+='<tr>'
								+'<td>'+rental.title+'</td>'
								+'<td>'+rental.author+'</td>'
								+'<td>'+rental.genre+'</td>'
								+'<td>'+rental.publisher+'</td>'
								+'<td>'+rental.deadline+'</td>'
								+'<td>'+'<button class="js-return" id='+rental.title+'>返却</button>'+'</td>'
								+'</tr>'

				$('#syainData').append(tableElement);
			}
			}

	});
	// ---------------ここまで---------------

}
$(document).ready(function () {
	'use strict';
	// 初期表示用
	 executeAjax();

});