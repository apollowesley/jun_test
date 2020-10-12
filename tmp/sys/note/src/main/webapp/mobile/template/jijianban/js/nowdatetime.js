var timerID = null;
var timerRunning = false;
function stopclock (){
if(timerRunning)
clearTimeout(timerID);
timerRunning = false;}
function startclock () {
stopclock();
showtime();}
function showtime () {
var now = new Date();
var yesrs=now.getFullYear();
var month=now.getMonth()+1;
var date=now.getDate();
var hours = now.getHours();
var minutes = now.getMinutes();
var seconds = now.getSeconds();
//var timeValue = "" +((hours >= 12) ? "AM" : "PM" )
var timeValue = "";
timeValue +=yesrs+".";
timeValue +=month+".";
timeValue +=date+" ";
timeValue += hours;
timeValue += ((minutes < 10) ? ":0" : ":") + minutes;
timeValue += ((seconds < 10) ? ":0" : ":") + seconds;
//timeValue = Date()
document.getElementById('thetime').innerHTML = timeValue;
timerID = setTimeout("showtime()",200);
timerRunning = true;}
//(startclock)(window);//加载到js就执行，无论页面是否加载完
window.onload=startclock;//页面加载完执行