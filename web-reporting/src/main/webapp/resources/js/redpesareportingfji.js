function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColors = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configHourToHour = {
    type: 'line',
    data: {
        labels: [0],
        datasets: [{
            label: "Revenue (Kes.)",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            data: [0],
            fill: false,
        }]
    },
    options: {
        responsive: true,
        tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>KES. <%=formatD(value)%>",
        scaleLabel: function (dgt) {
            return dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
        },
        scaleBeginAtZero: false,
        title:{
            display:true,
            text:'Hour-to-hour Revenue Comparison'
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
                    	label = label/1000;
                    	var formattednum = parseFloat(label, 10).toFixed(1).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
                        return formattednum+' k';
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

var updateGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/hourtohour',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configHourToHour.data.labels = respdata.labels;
    		configHourToHour.data.datasets[0].data = respdata.datasets[0].data;
    	},
    });
	
    if(window.myLine){
    	window.myLine.update();
    }
    
    setTimeout(updateGraph, 1950);
};

var initHourlyRevenue = function(){
	var canvas_ = document.getElementById("canvas");
	if(canvas_){
		var ctx = canvas_.getContext("2d");
		window.myLine = new Chart(ctx, configHourToHour);
	}
}
