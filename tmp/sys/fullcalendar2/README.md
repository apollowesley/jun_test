

### 事件与事件源

事件源指可加入到fullcalendar组件的事件的源，有4种：事件数组，用于生成事件的方法，返回json格式的url，Google Calendar feed。

			$('#calendar').fullCalendar({
				eventSources : []
			});
	
#### eventSources可以是：
			
	1. 事件数组
	
			{
			    events: [
			        {
			            title: 'Event1',
			            start: '2011-04-04'
			        },
			        {
			            title: 'Event2',
			            start: '2011-05-05'
			        }
			        // etc...
			    ],
			    color: 'yellow',   // an option!
			    textColor: 'black' // an option!
			}			
		
	1. 用于生成事件的函数
	
			{
			    events: function(start, end, callback) {
			        // ...
			    },
			    color: 'yellow',   // an option!
			    textColor: 'black' // an option!
			}	
		
	1. url 
	
			{
			    url: '/myfeed.php',
			    color: 'yellow',   // an option!
			    textColor: 'black' // an option!
			} 
			
    1. Google Calendar feed
    
	    	{
			    url: 'http://www.google.com/your_feed_url/',
			    color: 'yellow',   // an option!
			    textColor: 'black' // an option!
			}		


fullcalendar还有一个`events`属性，值可以为事件数组，用于生成事件的方法，返回json格式的url，Google Calendar feed

### 日历的选择

		//html
		<div id="calendar"></div>
		
		
		//js
		$('#calendar').fullCalendar({
			selectable:true,
			select:function(startDate, endDate, allDay, jsEvent, view ){
				//allDay Boolean值，是否包含末日期
				
			}
		}	

使用编程的方式选择date
	
		$('#calendar').fullCalendar( 'select', startDate, endDate, allDay );
		
### 日历的反选择

			//js
			$('#calendar').fullCalendar({
				unselect:function( view, jsEvent ){
					
				}
			}
			
使用编程的方式反选择date

	$('#calendar').fullCalendar( 'unselect' );
