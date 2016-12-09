function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsDailyTrax = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configdailytransactions = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Daily Avg.  Trx",
            backgroundColor: window.chartColorsDailyTrax.orange,
            borderColor: window.chartColorsDailyTrax.orange,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Daily Transactions",
            backgroundColor: window.chartColorsDailyTrax.green,
            borderColor: window.chartColorsDailyTrax.green,
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
            text:'Day-to-Day Transaction Comparison'
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
                    labelString: 'Day'
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

var updateDayOutLiveTrx  = function() {
	$.ajax({
    	url: '/redpesa-reporting/transactions?stats=sameday',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configdailytransactions.data.labels = respdata.labels;
    		configdailytransactions.data.datasets[0].data = respdata.datasets[0].data;
    		configdailytransactions.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.dailytransactions){
    	window.dailytransactions.update();
    }
    
    setTimeout(updateDayOutLiveTrx, 15000);
};

var initLiveDayOutLiveTrx = function(){
	var dailytransactions = document.getElementById("dailytransactions");
	if(dailytransactions){
		var ctx = dailytransactions.getContext("2d");
		window.dailytransactions = new Chart(ctx, configdailytransactions);
	}
	setTimeout(updateDayOutLiveTrx, 1700);
}