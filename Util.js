var ResultData;
var txt = '';
var script = '<script>';
// var script;
//var Role = 'finance';
var paramtest = 'ClassName=';
var chart_name;

var config = {
	// data: test_data,
	//xkey : 'year',
	//ykeys : [ 'value1', 'value2', 'value3', 'value4', 'value5' ],
	//	xkey : 'y',	
	//	ykeys : [ 'a', 'b'],
	labels : [ 'Total Income', 'Total Outcome' ],
	fillOpacity : 0.6,
	hideHover : 'auto',
	resize : true,
	pointFillColors : [ '#ffffff' ],
	pointStrokeColors : [ 'black' ],
	lineColors : [ 'red', 'green' ]

};

function loadProc(jsondata, ChartName, Title) {
	txt = '';
	script = '<script>';
	jsondata=

					config.data = jsondata;
					console.log("IN LOAD PROC.. JSONDATA: " + JSON.stringify(config.data) + "XKEY " + config.xkey + " YKEYS: " + config.ykeys + " CHART NAME: " + ChartName + " TITLE: " + Title);
			//		var Title = "<h1>" + Title + "</h1>"
					var ChartType = "<div id=\"" + ChartName
							+ "\"></div>";
					txt = txt + Title + ChartType;
					script = script + " config.element=\'"
							+ Title + "\';";

					if (ChartName == "AreaChart")
						script = script + "Morris.Area(config);";

					else if (ChartName == "Bar"){
						script = script + "Morris.Bar(config);";
		//				alert(script);
					}
					else
						script = script + "Morris.Line(config);";
					script = script + "</script>";
 //   console.log(txt+" ......"+script);
	$('#AreaChart').append(txt);
	alert(txt);
	$(document.body).append(script);
	alert(script);

}

$(document)
		.ready(
				function() {
					$('#AreaChart').append(txt);
					$('#AreaChart').append(script);

					$.each(JSON.parse(obj), function(idx, obj) { 
						if(obj.Title == "Area"){
							return;
						}
						paramtest = paramtest + obj.Title;
						chart_name = obj.ChartType;
						config.xkey = obj.xkey;
						config.ykeys = obj.ykeys;
						console.log("TITLE: " +paramtest + " CHART NAME: " + chart_name + " YKEYS: " + config.ykeys + "paramtest: " +paramtest);

											$
													.ajax({
														type : "GET",
														url : "http://localhost:8080/JerseyRestWebService/rest/service/dynamicjson",
														data : paramtest,
														dataType : "json",
														success : function(json) {
											//				console.log("HELLO --> " + JSON.stringify(json.data));
													//		alert(json.data);
															loadProc(
																	json.data,
																	obj.ChartType,
																	obj.Title
																	);

														},
														error : function(e) {
														}
													});
											paramtest = 'ClassName=';


				}); 

				});
