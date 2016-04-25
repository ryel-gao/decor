var curDate = new Date(),
	curDataObj = {},
	nextDataObj = {};

$(function(){
	//setRelatedValue( curDataObj );
	//var tempY = curDataObj._year;
	//var tempM = curDataObj._month+1;
	//if( curDataObj._month >= 11 ){
	//	tempY = tempY + 1;
	//	tempM = 0;
	//}
	//setRelatedValue( nextDataObj,tempY,tempM);
	//refreshMonthTitle($(".curmonth h3"),curDataObj._year,curDataObj._month);
	//refreshMonthTitle($(".nextmonth h3"),nextDataObj._year,nextDataObj._month);
	//curDataObj._curyear = curDataObj._year;
	//curDataObj._curmonth = curDataObj._month;
	//dInit();
});

function dInit(){	
	InitTimeTable( curDataObj,$(".curmonth") );
	InitTimeTable( nextDataObj,$(".nextmonth") );
	refreshTimeTableState(null,$(".curmonth"));
}

//设置日期相关的变量值
// [year,month,day] 传进来的值
function setRelatedValue( dObj,_year,_month ){
	if( _year != null || _year != undefined  ){
		curDate.setFullYear(_year, _month);
	}

	dObj._cur = curDate.getDate(); // 今天几号 0 ~ 31
	dObj._cur_day = curDate.getDay();
	curDate.setDate(1); // 把日期设置为当前月的1号
	dObj._year = curDate.getFullYear();
	dObj._month = curDate.getMonth(); // 0 ~ 11	
	dObj._day = curDate.getDay(); // 1号星期几  0（周日） 到 6（周六） 之间的一个整数
	dObj._days = getDaysForMonth(dObj._year,dObj._month); // 当前月有多少
	curDate.setDate( dObj._cur );
}


//初始化课程表结构
// dObj 日期对象
function InitTimeTable( dObj,domObj ){
	var $ttCell = domObj.find(".tt-body .tt-cell");
	$ttCell.removeClass("ing ed curday active-cell").removeAttr("weekday"); // 清除状态
	$ttCell.find("span").text(""); // 清除日期标记
	$ttCell.unbind("click");
	for(var i=1; i<=dObj._days; i++) {
		$ttCell.eq( i-1+dObj._day ).addClass("active-cell").attr("weekday", (i-1+dObj._day)%7 ).find("span").text(i);
		if(i == dObj._cur && dObj._month == dObj._curmonth && dObj._curyear == dObj._year ){
			$ttCell.eq( i-1+dObj._day ).addClass("curday"); // 标记当前日期

		}
	}
}

// 更新课表的状态
function refreshTimeTableState( data,domObj ){
	//var data = {1:2,5:1,8:2,9:1,16:1,19:2,20:1}; // 1 待上课、上课中 ,2 已完成
	for(var key in data){
		var _s = "ing";
		if( data[key] == 2 ){
			_s = "ed";
		}
		var _k = parseInt( key );		
		domObj.find(".tt-body .tt-cell.active-cell").eq(_k).addClass(_s);
	}
	//为可点击的单元格绑定事件
	domObj.find(".tt-body .tt-cell.active-cell.ing,.tt-body .tt-cell.active-cell.ed").bind("click",function(){
		var _text = $(this).find("span").text();
		var _date = $(this).attr("weekday");
		//setRelatedValue( "","",parseInt( _text ) );		
	});
}

function refreshMonthTitle(domObj,_year,_month){
	domObj.html(_year + "年" + (_month+1) + "月");
}

// 判断每个月有多少天
function getDaysForMonth(year,month){
	year = parseInt(year);
	month = month + 1;
	switch(month){
		case 2:
			if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){ // 闰年
				return 29;
			}else{ //平年
				return 28;
			}
			break;
		case 4:
			return 30;
			break;
		case 6:
			return 30;
			break;
		case 9:
			return 30;
			break;
		case 11:
			return 30;
			break;
		default:
			return 31;
			break;
	}
}
