window.chartColors = {
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};
var xx = 0;
window.randomScalingFactor = function() {
	//xx++;
	return (Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100);
}



var MONTHS = ["Janary", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
var config = {
    type: 'line',
    data: {
        labels: ["Janary", "February", "March", "April", "May", "June", "July"],
        datasets: [{
            label: "Dataset 1",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            data: [
                1, 
                2, 
                3, 
                4, 
                5, 
                6, 
                7
            ],
            fill: true,
        }, {
            label: "Dataset 2",
            fill: true,
            backgroundColor: window.chartColors.blue,
            borderColor: window.chartColors.blue,
            data: [
                8, 
                9, 
                10, 
                11, 
                12, 
                13, 
                14
            ],
        }]
    },
    options: {
        responsive: true,
        title:{
            display:true,
            text:'Mpesa Trx'
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
                    labelString: 'Transactions'
                }
            }]
        }
    }
};

window.onload = function() {
    var ctx = document.getElementById("canvas").getContext("2d");
    window.myLine = new Chart(ctx, config);
    updateGraph();
};

var updateGraph  = function() {
	console.log('here');
   /* config.data.datasets.forEach(function(dataset) {
    	 if (config.data.datasets.length > 0) {
    	        var month = MONTHS[config.data.labels.length % MONTHS.length];
    	        config.data.labels.push(month);

    	        config.data.datasets.forEach(function(dataset) {
    	            dataset.data.push(randomScalingFactor());
    	        });
    	 }

    });
*/
    if(window.myLine){
    	window.myLine.update();
    }
    setTimeout(updateGraph, 1000);
};

//setInterval(updateGraph,1000);