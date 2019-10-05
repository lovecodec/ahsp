function draw_index_vr(){
    var myChart1 = echarts.init(document.getElementById("vr"));
    option = {
        title : {
            text: '品种权',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            data: ['小麦','玉米','水稻','大豆','其他']
        },
        series : [
            {
                name: '各品种数量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'小麦'},
                    {value:310, name:'玉米'},
                    {value:234, name:'水稻'},
                    {value:135, name:'大豆'},
                    {value:300, name:'其他'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    myChart1.setOption(option);
}

function draw_index_variety() {
    var myChart = echarts.init(document.getElementById("variety"));
    var option = {
        title: {
            text: '已经采集审定品种'
        },
        tooltip: {},
        legend: {
            data:['品种数']
        },
        xAxis: {
            data: ["水稻","小麦","玉米","大豆","棉花","其他"]
        },
        yAxis: {},
        series: [{
            name: '品种数',
            type: 'bar',
            data: [1840, 813, 1680, 510,399,300]
        }]
    };
    myChart.setOption(option);
}

//variety.html绘制曲线
function draw_variety_count_year(){
    var myChart = echarts.init(document.getElementById("variety_count_year"));
    var option = {
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        title: {
            text: '历年审定品种数量曲线'
        },
        xAxis: {
            type: 'category',
            data: ['2005', '2006', '2007', '2008', '2009', '2010', '2011','2012','2013','2014'
                ,'2015','2016','2017','2018','2019'
            ]
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [1182, 1269, 1142, 1200, 1238, 1012, 1131,1090,971,1088,1057,1060,1736,2287,207],
            type: 'line'
        }]
    };
    myChart.setOption(option);
}

function draw_variety_count_type(){
    /*var myChart = echarts.init(document.getElementById("variety"));
    var option = {
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        title: {
            text: '历年审定品种数量曲线'
        },
        xAxis: {
            type: 'category',
            data: ['2005', '2006', '2007', '2008', '2009', '2010', '2011','2012','2013','2014'
                ,'2015','2016','2017','2018','2019'
            ]
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [1182, 1269, 1142, 1200, 1238, 1012, 1131,1090,971,1088,1057,1060,1736,2287,207],
            type: 'line'
        }]
    };
    myChart.setOption(option);*/
    var myChart = echarts.init(document.getElementById("varietyCount_type_year"));
    option = {
        title: {
            text: '安徽省各种类作物审定品种数量'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['大豆','小麦','棉花','水稻','玉米']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2005', '2006', '2007', '2008', '2009', '2010', '2011','2012','2013','2014'
                ,'2015','2016','2017','2018','2019'
            ]
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:'大豆',
                type:'line',
                stack: '总量',
                data:[0,1, 5, 1, 0, 6, 0, 4,7,1,0,10,2,7,0]
            },
            {
                name:'小麦',
                type:'line',
                stack: '总量',
                data:[0,0,2,2,4,1,3,12,3,8,0,21,22,0,0]
            },
            {
                name:'棉花',
                type:'line',
                stack: '总量',
                data:[6,12,6,4,5,9,1,1,15,3,1,1,1,0]
            },
            {
                name:'水稻',
                type:'line',
                stack: '总量',
                data:[24,21,7,14,7,56,20,19,24,24,0,35,24,19,0]
            },
            {
                name:'玉米',
                type:'line',
                stack: '总量',
                data:[6,8,2,7,9,2,14,11,13,16,0,39,23,11,0]
            }
        ]
    };
    myChart.setOption(option);
}

function draw_patent_count(){
    var myChart = echarts.init(document.getElementById("patent_count_year"));
    var option = {
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        title: {
            text: '历年专家申请专利情况'
        },
        xAxis: {
            type: 'category',
            data: ['2005', '2006', '2007', '2008', '2009', '2010', '2011','2012','2013','2014'
                ,'2015','2016','2017','2018','2019'
            ]
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [1182, 1269, 1142, 1200, 1238, 1012, 1131,1090,971,1088,1057,1060,1736,2287,207],
            type: 'line'
        }]
    };
    myChart.setOption(option);
}