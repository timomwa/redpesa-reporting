function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsLV = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configLiveviewsamehrinflow = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Hourly Avg. (Kes.)",
            backgroundColor: window.chartColorsLV.orange,
            borderColor: window.chartColorsLV.orange,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Hourly Rev (Kes.)",
            backgroundColor: window.chartColorsLV.purple,
            borderColor: window.chartColorsLV.grey,
            data: [0],
            fill: false,
        }]
    },
    type: 'bar',
    options: {
    	responsive : true,
		tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>KES. <%=formatD(value)%>",
		scaleLabel: function (dgt) {
            return dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,').toString();
        },
        scaleBeginAtZero: false,
        title:{
            display:true,
            text:'Hour-to-hour Inflow Comparison'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Hour'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Revenue (Kes.)'
                },
                ticks: {
                    callback: function(label, index, labels) {
                    	if(label>1000){
                    		label = label/1000;
                    		var formattednum = parseFloat(label, 10).toFixed(1).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
                    		return formattednum+' k';
                    	}
                    	return formattednum;
                    	
                    }
                },
                scaleLabel: {
                    display: true,
                    labelString:  'Revenue(Kes.) 1k = 1000'
                }
            }]
        }
    }
};

var updateLiveGraph  = function() {
	console.log('updateLiveGraph Called! ');
	$.ajax({
    	url: '/redpesa-reporting/hourtohour',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configLiveviewsamehrinflow.data.labels = respdata.labels;
    		configLiveviewsamehrinflow.data.datasets[0].data = respdata.datasets[0].data;
    		configLiveviewsamehrinflow.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.liveviewsamehrinflow){
    	window.liveviewsamehrinflow.update();
    }
    
    setTimeout(updateLiveGraph, 1567);
};

var initLiveGraphInflow = function(){
	var liveviewsamehrinflow = document.getElementById("liveviewsamehrinflow");
	if(liveviewsamehrinflow){
		var ctx = liveviewsamehrinflow.getContext("2d");
		window.liveviewsamehrinflow = new Chart(ctx, configLiveviewsamehrinflow);
	}
	setTimeout(updateLiveGraph, 1500);
}

