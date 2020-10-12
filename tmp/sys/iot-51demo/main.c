#include<reg52.h>
#include<string.h>
#include<intrins.h>
#include "74HC595.h"

char buf_string[64];  //定义数据包长度为15个字符
char clock[4] = {0,0,0,0}; //时间4位显示
char last_state = 0;
sbit LED=P2^6;
sbit LIGHT=P2^7;
//----------------------------------------------//

unsigned long Time = 0;
unsigned char str[]={0xC0,0xF9,0xA4,0xB0,0x99,0x92,0x82,0xF8,0x80,0x90};

/*******************************
            串口通信
    MCU:89C52RC        11.0592MHz

//11.0592MHz 0xd0 1200bps
//12MHz 0xcc 1200bps
//11.0592MHz 0xfa 9600bps
//0xf4 11.0592MHz  0xf3 12MHz 4800bps
//均在SMOD=1的情况下（波特率倍增模式）
*******************************/
//串口发送函数
void PutString(unsigned char *TXStr)  
{                
    ES=0;     
     while(*TXStr!=0) 
    {                      
        SBUF=*TXStr;
        while(TI==0);
        TI=0;    
        TXStr++;
    }
    ES=1; 
}
void PutChar(unsigned char c)
{
	ES=0;
	SBUF=c;
	while(TI==0);
	TI=0;
	ES=1;
}

unsigned long power(char a, char b)
{
	unsigned long count = 1;
	char i = 0;
	for(;i < b;i++)
	{
		count*=a;
	}
	return count;
}

unsigned long TransLong(char *str)
{
	unsigned long count = 0;
	unsigned char length = strlen(str);
	int i = length - 1;
	for(;i > 0; i--)
	{
		count+=(power(10,(length - 1 - i))*(str[i] - '0'));
	}
	return count;
}

void DealResult()
{
		char *buf = buf_string;
		if(buf_string[0] == 'i'){
			//调用结果
			//153908665
			unsigned long secMillis = TransLong(buf_string);
			unsigned char sec = (secMillis / 60) % 60;
			//我们是东八区
			unsigned char hour = ((secMillis / 60 + 8 * 60) / 60) % 24;
			clock[0] = hour / 10;
			clock[1] = hour % 10;
			clock[2] = sec / 10;
			clock[3] = sec % 10;

			
			
		}else if(buf_string[1] == 'c'){
			//命令执行
			if(0 == strcmp(buf,"con")){
				//点灯
				LED = 0;
			}else if(0 == strcmp(buf,"coff")){
				//灭灯
				LED = 1;
			}
		}
	
}


//定时器1用作波特率发生器
void Init_USART()  
{
		//strcmp("a","b");
    SCON=0x50;  //串口方式1，使能接收
    TMOD|=0x21;  //定时器1工作方式2（8位自动重装初值）
    TMOD&=~0x10;
    TH1=0xfa;   //9600bps
    TL1=0xfa;  
    PCON|=0x80;  //SMOD=1
    TR1=1;
    TI=0;
    RI=0;
    //PS=1;   //提高串口中断优先级
    ES=1;  //开启串口中断使能
}


/************************
        中断函数
************************/
//串口中断服务函数-----------
unsigned char wnum = 0;
unsigned char state = 0;
void USART() interrupt 4   //标志位TI和RI需要手动复位，TI和RI置位共用一个中断入口
{
    //if(ReceiveString())
    //{
        //数据包长度正确则执行以下代码
    //    Deal_UART_RecData();   
    //}
    char tmp;
		tmp = SBUF;
		if('$' == tmp)
		{
			//结束开始标志位
			state = 1;
		}else if('{' == tmp && state == 1){
			state = 2;
		}else if(state == 2)
		{
			//i:invoke 远程调用 c:command 命令下发
			if('i' == tmp || 'c' == tmp)
			{
				//第一位是信息标志位，标识着是哪种类型的标志
				state = 3;
				buf_string[0] = tmp;
				wnum++;
			}
			//这里其实忽略了一位，但是不会影响
		}else if(state == 3){
			if('s' == tmp){
				state = 4;
			}else if('e' == tmp){
				state = 99;
			}
		}else if('}' == tmp && state == 4){
			//开始写入buff
			state = 5;
			wnum = 1;
		}else if(state == 5){
			buf_string[wnum] = tmp;
			wnum++;
			if(wnum == 63){
				buf_string[63] = '\0';
				state = 100;
			}
		}else if(tmp == '}' && state == 99){
			state = 100;
			buf_string[wnum] = '\0';
		}
		
		if(state == 100){
			//写入完成,开始处理逻辑
			DealResult();
			
		}
    RI=0;  //接收并处理一次数据后把接收中断标志清除一下，拒绝响应在中断接收忙的时候发来的请求
}
/***************************
        主函数
***************************/
void main()
{
		unsigned char i = 0, j = 0, k = 0, state = 0;
		unsigned char dis_num[4];
		dis_num[0]=0x00;
		dis_num[1]=0xff;
    EA=1;
    Init_USART();
		PutString("System init\r\n");
//		Hc595SendByte(0xff);
		
    while(1)
    {
				PutString("invoke 3241a641ebb443f6b1addd6daea21874 getGmt\r\n");
				for(i = 0; i <  60; i++)
				{
					for(j = 0; j < 150; j++)
					{
						display(str[clock[0]],0x08);
						display(str[clock[1]],0x04);
						display(str[clock[2]],0x02);
						display(str[clock[3]],0x01);
						//取一个瞬时值，防止抖动
						state = LIGHT;
						if(state != last_state)
						{
							if(1 == state)
							{
								PutString("param light 0\r\n");
							}
							else
							{
								PutString("param light 1\r\n");
							}
							last_state = state;
						}
						
					}		
				}
					
			
				
			
				
			
//			display(0xf0);
//			Delay10ms(100);
//			Writ8bit(0x0f);
        //扫描更新数码管
//			for(i = 0;i>3;i++)
//			{
					
//				WriteWord(Digit0,displayTime/(60*60)/10);	//显示小时的十位
//				WriteWord(Digit1,displayTime/(60*60)%10);	//显示小时的个位
//				displayTime=displayTime%(60*60);			//取出分钟数
//				WriteWord(Digit3,displayTime/60/10);		//显示分钟的十位
//				WriteWord(Digit4,displayTime/60%10);		//显示分钟的个位
//				displayTime=displayTime%60; 				//取出秒钟数
//				WriteWord(Digit6,displayTime/10);			//显示秒钟的十位
//				WriteWord(Digit7,displayTime%10);			//显示秒钟的个位
//				delay(100);
	//		}
			
    }
}


//void Timer0(void) interrupt 1      //定时器0 中断函数
//{
//	 TH0 = (65536 - 10000)/256;
//	 TL0 = (65536 - 10000)%256;			//每50ms产生一次中断
// 	 Time=Time+10;	//每次中断时间加50ms
//	 if (Time > 86400000) Time=Time%86400000;  //超过24小时，时间对24小时取余数
//}
//void timerInit()	 //定时器初始化
//{
//     TMOD = 0x21; //选择模式1  16位计数 最大计数65536

//		 TH0 = (65536 - 100000)/256;
//		 TL0 = (65536 - 100000)%256;
//     IE = 0x8A;   //开启定时器中断	
//     TR0  = 1;    //开启T0定时器
//}