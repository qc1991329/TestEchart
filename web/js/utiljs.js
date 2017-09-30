/**
 * Created by Administrator on 2017-9-28.
 */
function getOption (chartType, yModel, xSalCount, title) {
    var chartOption =
        chartType == 'bar' ? {
            calculable: true,
            legend: {
                data:[title]
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'value'
                }
            ],
            yAxis : [
                {
                    type : 'category',
                    axisTick : {show: false},
                    data : yModel
                }
            ],
            series : [
                {
                    name: title,
                    type: chartType,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'inside'
                            },
                            color: 'rgba(72,118,255,255)'
                        }
                    },
                    data: xSalCount
                }
            ]
        } :
            {
                legend: {
                    data: [title]
                },

                grid: {
                    x: 55,
                    x2: 10,
                    y: 30,
                    y2: 25
                },
                toolbox: {
                    show: false,
                    feature: {
                        mark: {
                            show: true
                        },
                        dataView: {
                            show: true,
                            readOnly: false
                        },
                        magicType: {
                            show: true,
                            type: ['line', 'bar']
                        },
                        restore: {
                            show: true
                        },
                        saveAsImage: {
                            show: true
                        }
                    }
                },
                calculable: true,
                xAxis: [{
                    type: 'category',
                    data: yModel,
                    axisLabel: {
                        show:true,
                        interval: 0
                    }
                }],
                yAxis: [{
                    type: 'value',
                    splitArea: {
                        show: true
                    },
                }],
                series: [
                    {
                        name: title,
                        type: chartType,
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    position: 'top'
                                },
                                color: 'rgba(72,118,255,255)'
                            }
                        },
                        data: xSalCount
                    }
                ]
            };
    return chartOption;
}

function byId (id) {
    return document.getElementById(id);
}

function getdatestring (date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1 >= 10?(date.getMonth()+1)+'':'0' + (date.getMonth()+1);
    var d = date.getDate() >= 10?date.getDate()+'':'0' + date.getDate();
    return y+m+d;
}
