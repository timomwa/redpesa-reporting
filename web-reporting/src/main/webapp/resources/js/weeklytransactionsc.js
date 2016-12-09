function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsWeeklyTrax = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(0, 128, 0)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configweeklytransactions = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Weekly Avg.  Trx",
            backgroundColor: window.chartColorsWeeklyTrax.blue,
            borderColor: window.chartColorsWeeklyTrax.blue,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Week Transactions",
            backgroundColor: window.chartColorsWeeklyTrax.green,
            borderColor: window.chartColorsWeeklyTrax.green,
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
            text:'Week-to-Week Transaction Comparison'
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
                    labelString: 'Week'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'C2B Transactions'
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
                    labelString:  'Transactions 1k = 1000'
                }
            }]
        }
    }
};

var updateWeekOutLiveTrx  = function() {
	$.ajax({
    	url: '/redpesa-reporting/transactions?stats=weekly',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configweeklytransactions.data.labels = respdata.labels;
    		configweeklytransactions.data.datasets[0].data = respdata.datasets[0].data;
    		configweeklytransactions.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.weeklytransactions){
    	window.weeklytransactions.update();
    }
    
    setTimeout(updateWeekOutLiveTrx, 15000);
};

var initLiveWeekOutLiveTrx = function(){
	var weeklytransactions = document.getElementById("weeklytransactions");
	if(weeklytransactions){
		var ctx = weeklytransactions.getContext("2d");
		window.weeklytransactions = new Chart(ctx, configweeklytransactions);
	}
	setTimeout(updateWeekOutLiveTrx, 1700);
}

window.onload = function() {
	initLiveDayOutLiveTrx();
	initLiveWeekOutLiveTrx();
	
	updateDayOutLiveTrx();
	updateWeekOutLiveTrx();
}
