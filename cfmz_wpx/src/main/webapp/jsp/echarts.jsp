<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<title>ECharts</title>
<!-- 引入 echarts.js -->
<script src="../echarts/echarts.js"></script>
<script src="../boot/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="https://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<script src="https://www.echartsjs.com/gallery/vendors/echarts/map/js/china.js?_v_=1553896255267"></script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<div id="area" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    $(function(){
        echartsUser();

        var goEasy = new GoEasy({
            appkey: 'BC-14b0dee32f8f49cbaf18ab411780d1c5'
        });
        goEasy.subscribe({
            channel:'demo_channel',
            onMessage: function(message){
                var data = JSON.parse(message.content);
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                var chart = echarts.init(document.getElementById('area'));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '注册统计'
                    },
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    xAxis: {
                        data: data.month
                    },
                    yAxis: {},
                    series: [{
                        name: '注册人数',
                        type: 'bar',
                        data: data.month2
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
                areaOption = {
                    title : {
                        text: '注册地区',
                        subtext: '',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['男','女']
                    },
                    visualMap: {
                        min: 0,
                        max: 2500,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : [
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:data.man
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:data.women
                        }
                    ]
                };
                // 这是地图的图表
                chart.setOption(areaOption);
            }
        });

    })

    function goeasy() {
    }

    function echartsUser() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user/queryByMonth",
            type:"post",
            datatype:"json",
            success:function(data){
                console.log(data)
                var linNames=[];
                var linNums=[];
                var manNames=[{}];
                var womenNames=[{}];
                $.each(data.month,function(key,values){

                    linNames.push(values.month);
                    linNums.push(values.count);

                });
                $.each(data.man,function(key,values){

                    manNames.push({name:values.province,value:values.count});

                });
                $.each(data.women,function(key,values){

                    womenNames.push({name:values.province,value:values.count});

                });
                /* for (i in data){
                     linNames.push(data[i].month);
                     linNums.push(data[i].count);
                     i++;
                 }*/
                //柱形图赋值
                myChart.setOption({ //加载数据图表
                    xAxis: {
                        data: linNames
                    },
                    series: {
                        // 根据名字对应到相应的系列
                        name: ['数量'],
                        data: linNums
                    }
                });
                chart.setOption({
                    series : [
                        {
                            name: '男',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:manNames
                        },
                        {
                            name: '女',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:womenNames
                        }
                    ]
                });

            },



        })
    }
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var chart = echarts.init(document.getElementById('area'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '注册统计'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '注册人数',
            type: 'bar',
            data: []
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    areaOption = {
        title : {
            text: '注册地区',
            subtext: '',
            left: 'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data:['男','女']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            left: 'right',
            top: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                name: '男',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[]
            },
            {
                name: '女',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data:[]
            }
        ]
    };
    // 这是地图的图表
    chart.setOption(areaOption);



</script>

<br/><br/><br/><br/><br/><br/><br/>
