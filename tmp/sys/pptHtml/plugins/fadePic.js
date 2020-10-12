var getEle = function(id) {
	return "string" === typeof id ? document.getElementById(id) : id;
};

if (!isIE) {
	// 这个位置的含义不是很清楚？？？需要好好研究一下…………
	HTMLElement.prototype.__defineGetter__("currentStyle", function() {
		return this.ownerDocument.defaultView.getComputedStyle(this, null);
	});
}

// 这个东西有点像类制造工厂
var Class = {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		}
	}
}

// 复制拷贝性质的代码
Object.extend = function(destination, source) {
	for ( var property in source) {
		destination[property] = source[property];
	}
	return destination;
}

var FadeStruct = function(options) {
	this.run = false;
	// 是否渐变
	this.start = 0;
	// 开始值
	this.end = 0;
	// 结束值
	this.target = 0;
	// 目标值
	Object.extend(this, options || {});
}

// 下面定义 渐变对象 Fade
// 为什么要采用下面方法定义Fade类，我认为最根本的原因是：需要往构造函数中传递参数。
var Fade = Class.create();

Fade.prototype = {
	initialize : function(obj, options) {
		var obj = getEle(obj);
		obj.style.overflow = "hidden";
		this._obj = obj;
		this._timer = null;
		// 定时器
		this._finish = true;
		// 是否执行完成
		this._fun = function() {
		};
		// 渐变程序
		this._x = this._y = 0;
		// 变换点位置
		// 设置获取透明度程序
		this._setOpacity = isIE ? function(opacity) {
			obj.style.filter = "alpha(opacity:" + opacity + ")";
		} : function(opacity) {
			obj.style.opacity = opacity / 100;
		};
		this._getOpacity = isIE ? function() {
			return parseInt(obj.filters["alpha"].opacity);
		} : function(opacity) {
			return 100 * parseFloat(obj.currentStyle.opacity);
		};
		// 获取边框宽度程序
		this._xBorder = function() {
			return (parseInt(obj.currentStyle.borderLeftWidth) + parseInt(obj.currentStyle.borderRightWidth));
		}
		this._yBorder = function() {
			return (parseInt(obj.currentStyle.borderTopWidth) + parseInt(obj.currentStyle.borderBottomWidth));
		}
		this.SetOptions(options);
		this.Mode = this.options.Mode;
		this.Time = Math.abs(this.options.Time);
		this.onFinish = this.options.onFinish;
		// 先设置特殊默认值
		this.Opacity = new FadeStruct({
			end : 100
		});
		this.Top = new FadeStruct({
			start : this._obj.offsetTop,
			end : this._obj.offsetTop
		});
		this.Left = new FadeStruct({
			start : this._obj.offsetLeft,
			end : this._obj.offsetLeft
		});
		this.Height = new FadeStruct({
			end : this._obj.offsetHeight - this._yBorder()
		});
		this.Width = new FadeStruct({
			end : this._obj.offsetWidth - this._xBorder()
		});
		// 再设置用户默认值
		Object.extend(this.Opacity, this.options.Opacity);
		Object.extend(this.Top, this.options.Top);
		Object.extend(this.Left, this.options.Left);
		Object.extend(this.Height, this.options.Height);
		Object.extend(this.Width, this.options.Width);
		// 变换位置参数
		this.Height.pos = Number(this.options.Height.pos);
		this.Width.pos = Number(this.options.Width.pos);
		// 设置成默认状态
		this.Show = !this.options.Show;
		this.Step = 1;
		this.Start();
		// 重新设置Step
		this.Step = Math.abs(this.options.Step);
	},
	// 设置默认属性
	SetOptions : function(options) {
		this.options = {// 默认值
			Opacity : {}, // 透明渐变参数
			Height : {}, // 高度渐变参数
			Width : {}, // 宽度渐变参数
			Top : {}, // Top渐变参数
			Left : {}, // Left渐变参数
			Step : 10, // 变化率
			Time : 10, // 变化间隔
			Mode : "both", // 渐变顺序
			Show : false, // 是否默认打开状态
			onFinish : function() {
			}// 完成时执行
		};
		Object.extend(this.options, options || {});
	},
	// 触发
	Start : function() {
		clearTimeout(this._timer);
		// 取反表示要设置的状态
		this.Show = !this.Show;
		// 为避免透明度为null值，需要先设置一次透明度
		if (this.Opacity.run) {
			this._setOpacity(this.Show ? this.Opacity.start : this.Opacity.end);
		}
		// 根据状态设置目标值
		if (this.Show) {
			this.Opacity.target = this.Opacity.end;
			this.Top.target = this.Top.end;
			this.Left.target = this.Left.end;
			this.Height.target = this.Height.end;
			this.Width.target = this.Width.end;
		} else {
			this.Opacity.target = this.Opacity.start;
			this.Top.target = this.Top.start;
			this.Left.target = this.Left.start;
			this.Height.target = this.Height.start;
			this.Width.target = this.Width.start;
		}
		// 设置渐变程序
		switch (this.Mode.toLowerCase()) {
		case "width":
			this._fun = function() {
				this.SetWidth() && this.SetHeight();
				// 由于分了两步，下面的步长变成两倍
				this.Step = 2 * this.Step;
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
				this.Step = this.Step / 2;
			}
			break;
		case "height":
			this._fun = function() {
				his.SetHeight() && this.SetWidth();
				// 由于分了两步，下面的步长变成两倍
				this.Step = 2 * this.Step;
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
				this.Step = this.Step / 2;
			}
			break;
		case "both":
		default:
			this._fun = function() {
				this.SetHeight();
				this.SetWidth();
				this.SetOpacity();
				this.SetTop();
				this.SetLeft();
			}
		}
		// 获取变换点位置
		// 由于设置变换点后与top和left变换有冲突只能执行其一
		if (this.Height.pos) {
			this._y = this._obj.offsetTop + this._obj.offsetHeight
					* this.Height.pos;
			this.Top.run = false;
		}
		if (this.Width.pos) {
			this._x = this._obj.offsetLeft + this._obj.offsetWidth
					* this.Width.pos;
			this.Left.run = false;
		}
		this.Run();
	},
	// 执行
	Run : function() {
		clearTimeout(this._timer);
		this._finish = true;
		// 执行渐变
		this._fun();
		// 未完成继续执行
		if (this._finish) {
			this.onFinish();
		} else {
			var oThis = this;
			this._timer = setTimeout(function() {
				oThis.Run();
			}, this.Time);
		}
	},
	// 设置高度渐变
	SetHeight : function() {
		var iGet = this.Get(this.Height, this._obj.offsetHeight
				- this._yBorder());
		if (isNaN(iGet))
			return true;
		this._obj.style.height = iGet + "px";
		// 如果有变换点设置
		if (this.Height.pos) {
			this._obj.style.top = this._y - this._obj.offsetHeight
					* this.Height.pos + "px";
		}
		return false;
	},
	// 设置宽度渐变
	SetWidth : function() {
		var iGet = this
				.Get(this.Width, this._obj.offsetWidth - this._xBorder());
		if (isNaN(iGet))
			return true;
		this._obj.style.width = iGet + "px";
		if (this.Width.pos) {
			this._obj.style.left = this._x - this._obj.offsetWidth
					* this.Width.pos + "px";
		}
		return false;
	},
	// 设置top渐变
	SetTop : function() {
		var iGet = this.Get(this.Top, this._obj.offsetTop);
		if (isNaN(iGet))
			return true;
		this._obj.style.top = iGet + "px";
		return false;
	},
	// 设置left渐变
	SetLeft : function() {
		var iGet = this.Get(this.Left, this._obj.offsetLeft);
		if (isNaN(iGet))
			return true;
		this._obj.style.left = iGet + "px";
		return false;
	},
	// 设置透明渐变
	SetOpacity : function() {
		var iGet = this.Get(this.Opacity, this._getOpacity());
		if (isNaN(iGet))
			return true;
		this._setOpacity(iGet);
		return false;
	},
	// 获取设置值
	Get : function(o, now) {
		if (o.run) {
			var iStep = (o.target - now) / this.Step;
			if (iStep) {
				this._finish = false;
				if (Math.abs(iStep) < 1) {
					iStep = iStep > 0 ? 1 : -1;
				}
				return now + iStep;
			}
		}
	}
};
