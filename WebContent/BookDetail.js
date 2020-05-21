// AjaxでJSONを取得する
function executeAjax () {
	'use strict';

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
		dataType : 'json',
		data :requestQuery,
		success : function (json) {
			for (var i = 0; i < json.length; i++) {


				var elements = json[i];

				$('#js-title').html(elements.title);
				$('#js-author').html(elements.author);
				$('#js-publisher').html(elements.publisher);
				$('#js-genre').html(elements.genre);
				$('#js-status').html(elements.status);

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

$(document).ready(function () {
	'use strict';

	// 初期表示用
	executeAjax();

	//$('#table_data').ready('road',executeAjax);


});