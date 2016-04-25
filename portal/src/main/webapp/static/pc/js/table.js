/* 左右点击切换列表 */
function HorizontalList( configparam ){
	this.config = {
		leftArrow : null ,  //配置左箭头
		rightArrow : null   //配置右箭头
	};
	var _self = this;
	_self.scrollDirection = 1; // 1 向左滚动; 2 向右滚动
	_self.scrollIndex = 0; // 当前滚动序号
	if( configparam ){
		$.extend( this.config,configparam );
	}		
	// 类初始化
	_self.init( configparam );
	_self.leftArrow = _self.wrapObj.find( this.config.leftArrow );
	_self.rightArrow = _self.wrapObj.find( this.config.rightArrow );	
	//初始自动执行
	(function() {
		// body...
		if( _self.Len <= _self.scrollStep ){
			if( _self.leftArrow ){
				_self.leftArrow.hide();
			}
			if( _self.leftArrow ){
				_self.rightArrow.hide();
			}			
			return;
		}	
	})();
	

	if( _self.leftArrow ){
		_self.leftArrow.unbind("click").bind("click",function(){
			if( _self.listObj.is(":animated") || $(this).hasClass("disabled") ) return;					
			_self.scrollLeft(); //左滑动
			if( _self.scrollTimer ){
				clearInterval( _self.scrollTimer );
				_self.scrollAutoFunc();
			}			
		});
	}
	
	if( _self.rightArrow ){
		_self.rightArrow.unbind("click").bind("click",function(){
			if( _self.listObj.is(":animated") || $(this).hasClass("disabled") ) return;
			_self.scrollRight(); //右滑动
			if( _self.scrollTimer ){
				clearInterval( _self.scrollTimer );
				_self.scrollAutoFunc();
			}	
		});
	}

	this.unsignScrollState = function() {
		if( _self.scrollDirection == 1 ){ //向左翻动
			_self.rightArrow.removeClass("disabled");			
		} else if( _self.scrollDirection == 2 ){ //向右翻动
			_self.leftArrow.removeClass("disabled");
		}
	}

	this.signScrollState = function() {
		if( _self.scrollDirection == 1 && _self.scrollIndex == 0 ){ //向左翻动
			_self.rightArrow.addClass("disabled");			
		} else if( _self.scrollDirection == 2 && _self.scrollIndex == ((_self.Len % _self.scrollStep == 0 ) ? (_self.Len / _self.scrollStep - 1):Math.floor(_self.Len / _self.scrollStep)) ){  //向右翻动
			_self.leftArrow.addClass("disabled");
		}		
	}

	if( this.config.scrollAutoFlag ){ //自动滚动
		_self.scrollAutoFunc();	
	}
	
}

/* 带导航列表 */
function HorizontalListwidthNav( configparam ){
	this.config = {
		promoNavName : ""   //图片导航样式名称
	};
	var _self = this;
	_self.scrollDirection = 1; // 1 向左滚动; 2 向右滚动
	_self.scrollIndex = 0; // 当前滚动序号	
	if( configparam ){
		$.extend( this.config,configparam );
	}
	// 类初始化
	_self.init( configparam );
	//初始自动执行
	(function(argument) {
		// body...
		if( _self.Len <= _self.scrollStep ){			
			return;
		}	
	})();	

	if( _self.config.promoNavName ){ 	//图片导航	 
		var _html = '';
		for(var i=0; i<this.Len; i++){
			_html += '<span>'+(i+1)+'</span>'
		}
		$('<div class="'+_self.config.promoNavName+'"></div>').prependTo( _self.wrapObj );
    	$("."+_self.config.promoNavName).append( _html );
    	_self.navUnit = $("."+_self.config.promoNavName).children();
    	_self.navUnit.eq(0).addClass("current");
    	_self.navUnit.mouseenter(function(){
    		var _index = $(this).index(); // 当前选择的序号
    		_self.navUnit.removeClass("current");
    		_self.navUnit.eq( _index ).addClass("current");
    		_self.listObj.stop();
    		if( _self.scrollTimer ){
				clearInterval( _self.scrollTimer );
			}
			if( _index > _self.scrollIndex ){ //如果当前选择的后一个，向左翻
				_self.scrollLeft( _index - _self.scrollIndex );
			} else { 
				_self.scrollRight( _self.scrollIndex - _index );  //如果当前选择的前一个，向右翻
			}
				
    	}).mouseleave(function(){ //鼠标移开
    		if( _self.config.scrollAutoFlag ){ //自动滚动			
				_self.scrollAutoFunc();	
			}
    	});
	}

	this.unsignScrollState = function() {
		_self.navUnit.removeClass("current");		
	}

	this.signScrollState = function() {
		if( _self.scrollIndex >= _self.scrollLen ){
			_self.scrollIndex = _self.scrollLen - 1;
		} else if( _self.scrollIndex < 0) {
			_self.scrollIndex = 0;
		}
		_self.navUnit.eq( _self.scrollIndex ).addClass("current");		
	}

	if( this.config.scrollAutoFlag ){ //自动滚动
		_self.scrollAutoFunc();	
	}	
}

/**
* 水平列表原型
* 方法
*/
HorizontalList.prototype = {
	init : function( configparam ){
		var _self = this;
		this.config = {
			wrapObj : null, 	//配置最外层包裹对象
			innerObj : null, 	//配置内层包裹对象
			listObj : null, 	//配置列表包裹对象
			scrollElements : null, 
			scrollAutoFlag : false, //是否自动滚动
			animateTime : 500,  //滚动时间
			spaceTime : 4000,  //自动滚动时的间隔时间
			scrollStep : 1, 	//一次滚动1个单位
			dValue : 0
		};
		if( configparam ){
			$.extend( this.config,configparam );
		}
		_self.wrapObj = this.config.wrapObj;
		_self.innerObj = _self.wrapObj.find( this.config.innerObj );
		_self.listObj = _self.wrapObj.find( this.config.listObj );
		_self.listLi =  _self.wrapObj.find( this.config.scrollElements );
		_self.scrollStep = this.config.scrollStep;
		_self.oneW = _self.listLi.outerWidth( true );
		_self.Len = _self.listLi.length;
		_self.scrollLen = Math.ceil( _self.Len / _self.scrollStep );
		_self.totalW = _self.oneW * _self.Len;
		_self.viewW = _self.innerObj.width() + this.config.dValue;
		_self.MarginR = parseInt( _self.listLi.eq(0).css("margin-right") );
		_self.scrollStepW = _self.scrollStep * _self.oneW;
	},
	getMarginLeft: function(){
		return parseInt( this.listObj.css("margin-left") ); 
	},
	scrollLeft : function( $scrollstep ){	// $scrollstep 选择序号时传的值
		var _self = this;
		var _step = 1;	
		var _MLeft = - _self.scrollStepW * _self.scrollIndex;
		_self.unsignScrollState(); // 取消滚动标记		
		if( $scrollstep != undefined ){ // 选择了滚动序号
			_self.scrollIndex = _self.scrollIndex + $scrollstep;
			_step = $scrollstep;
		} else {
			_self.scrollIndex++; // 当前滚动序号
		}
		if( _MLeft - _self.scrollStepW * _step <= _self.viewW + _self.MarginR - _self.totalW ){
			_self.listObj.animate({ "marginLeft" : _self.viewW + _self.MarginR - _self.totalW },_self.config.animateTime);
			_self.scrollDirection = 2; 
						
		} else {
			_self.listObj.animate({ "marginLeft" : _MLeft - _self.scrollStepW * _step },_self.config.animateTime);
		}
		_self.signScrollState(); // 添加滚动标记
	},
	scrollRight : function( $scrollstep ){		
		var _self = this;
		var _step = 1;	
		var _MLeft = - _self.scrollStepW * _self.scrollIndex;
		_self.unsignScrollState(); // 取消滚动标记
		if( $scrollstep != undefined ){ // 选择了滚动序号
			_self.scrollIndex = _self.scrollIndex - $scrollstep;
			_step = $scrollstep;
		} else {
			_self.scrollIndex--; // 当前滚动序号
		}		
		if( _MLeft + _self.scrollStepW * _step >= 0 ){
			_self.listObj.animate({ "marginLeft" : 0 },_self.config.animateTime);	
			_self.scrollDirection = 1;
					
		} else {
			_self.listObj.animate({ "marginLeft" : _MLeft + _self.scrollStepW * _step },_self.config.animateTime);
		}
		_self.signScrollState(); // 添加滚动标记
	},
	scrollAutoFunc : function(){
		var _self = this;
		_self.scrollTimer = setInterval(function(){
			if( _self.scrollDirection == 1 ){ //向左翻
				_self.scrollLeft();
			} else if( _self.scrollDirection == 2 ){ //向右翻
				_self.scrollRight();
			}
		}, _self.config.spaceTime);
	}

}

HorizontalListwidthNav.prototype = HorizontalList.prototype;







